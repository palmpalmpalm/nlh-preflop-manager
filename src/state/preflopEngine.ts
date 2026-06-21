import { useSyncExternalStore } from "react";
import { comboIndex, comboLabel, NLH_RANKS, positionsForPlayerCount } from "../core/cards";
import {
  cloneMatrix,
  describeMatrix,
  fullMatrix,
  matrixFromText,
  matrixDensity,
  multiplyMatrices
} from "../core/range";
import { loadLatestSnapshot, saveLatestSnapshot } from "../core/storage";
import type {
  Bookmark,
  EngineSettings,
  EngineView,
  NodeId,
  RangeCellView,
  SerializedAppState,
  SerializedTreeNode,
  TreeNode,
  ViewMode
} from "../core/types";

const ACTION_COLORS = [
  "#f45b3f",
  "#b72d42",
  "#f08a24",
  "#8b5cf6",
  "#2f80ed",
  "#00a7a7",
  "#d946ef",
  "#7c3aed"
];
const CALL_COLOR = "#53d96b";
const FOLD_COLOR = "#c9ced6";
const CELL_TEXT_LIGHT = "#f7fafc";
const CELL_TEXT_DARK = "#151718";

type Listener = () => void;

interface ActionDraft {
  kind: "raise" | "call";
  range: Uint8Array;
  raiseTo?: number;
}

class PreflopEngine {
  private settings: EngineSettings = defaultSettings();
  private nodes = new Map<NodeId, TreeNode>();
  private listeners = new Set<Listener>();
  private weightCache = new Map<NodeId, Map<string, Uint8Array>>();
  private rootId: NodeId = "n1";
  private currentId: NodeId = "n1";
  private nextSeq = 2;
  private bookmarks: Bookmark[] = [];
  private viewMode: ViewMode = "absolute";
  private autoRandom = false;
  private rng = 50;
  private version = 0;
  private status = "Ready";
  private isBusy = false;
  private busyLabel: string | null = null;
  private view: EngineView;

  constructor() {
    this.reset();
    this.view = this.makeView();
  }

  subscribe = (listener: Listener): (() => void) => {
    this.listeners.add(listener);
    return () => this.listeners.delete(listener);
  };

  getSnapshot = (): EngineView => this.view;

  reset(playerCount: 2 | 6 = 6, startingStack = 100): void {
    this.settings = {
      startingStack,
      playerCount,
      positions: positionsForPlayerCount(playerCount),
      ranks: NLH_RANKS
    };
    this.nodes.clear();
    this.weightCache.clear();
    this.nextSeq = 1;
    this.rootId = this.makeId();
    this.currentId = this.rootId;
    this.bookmarks = [];
    this.status = "New tree created";

    const stacks = this.settings.positions.reduce<Record<string, number>>((acc, position) => {
      acc[position] = startingStack;
      return acc;
    }, {});
    stacks.SB = startingStack - 0.5;
    stacks.BB = startingStack - 1;

    const active = this.settings.positions.reduce<Record<string, boolean>>((acc, position) => {
      acc[position] = true;
      return acc;
    }, {});

    this.nodes.set(this.rootId, {
      id: this.rootId,
      parentId: null,
      children: [],
      kind: "blind",
      position: "BB",
      round: 0,
      pot: 1.5,
      toCall: 1,
      stacks,
      active,
      createdAt: Date.now()
    });

    this.ensureFoldChild(this.rootId);
    this.refresh();
  }

  createNewTree(playerCount: 2 | 6, startingStack: number): void {
    this.reset(playerCount, startingStack);
  }

  setViewMode(viewMode: ViewMode): void {
    this.viewMode = viewMode;
    this.status = viewMode === "weighted" ? "Weighted mode" : "Absolute mode";
    this.refresh();
  }

  setAutoRandom(autoRandom: boolean): void {
    this.autoRandom = autoRandom;
    this.status = autoRandom ? "Randomizer enabled" : "Randomizer disabled";
    this.refresh();
  }

  rollRng(): void {
    this.rng = Math.floor(Math.random() * 100) + 1;
    this.refresh(false);
  }

  navigate(nodeId: NodeId): void {
    if (!this.nodes.has(nodeId)) return;
    this.currentId = nodeId;
    this.ensureFoldChild(nodeId);
    this.status = `Viewing ${this.nodeLabel(this.getNode(nodeId))}`;
    this.refresh();
  }

  navigateRoot(): void {
    this.navigate(this.rootId);
  }

  addBookmark(label?: string): void {
    const node = this.getCurrentNode();
    const text = label?.trim() || this.nodeLabel(node);
    this.bookmarks = [
      ...this.bookmarks.filter((bookmark) => bookmark.nodeId !== node.id),
      { id: `b${Date.now()}`, label: text, nodeId: node.id }
    ];
    this.status = `Bookmarked ${text}`;
    this.refresh();
  }

  removeBookmark(id: string): void {
    this.bookmarks = this.bookmarks.filter((bookmark) => bookmark.id !== id);
    this.status = "Bookmark removed";
    this.refresh();
  }

  deleteChild(nodeId: NodeId): void {
    const node = this.nodes.get(nodeId);
    if (!node || node.kind === "fold" || !node.parentId) return;

    const parent = this.getNode(node.parentId);
    parent.children = parent.children.filter((childId) => childId !== nodeId);
    this.deleteSubtree(nodeId);
    this.weightCache.clear();
    this.status = "Action deleted";
    this.refresh();
  }

  addAction(rangeText: string, sizeText: string): void {
    const current = this.getCurrentNode();
    const nextActor = this.nextAvailablePosition(current);
    if (!nextActor || !this.canNodeAct(current, nextActor)) {
      this.status = "No legal action is available from this node";
      this.refresh();
      return;
    }

    let action: ActionDraft;
    try {
      action = this.parseActionDraft(rangeText, sizeText);
    } catch (error) {
      this.status = error instanceof Error ? error.message : "Invalid action";
      this.refresh();
      return;
    }

    const child = this.createActionNode(current, nextActor, action);
    if (!child) {
      this.status = "Action is not legal from the current node";
      this.refresh();
      return;
    }

    current.children = [...current.children.filter((id) => this.getNode(id).kind !== "fold"), child.id];
    this.nodes.set(child.id, child);
    this.sortChildren(current);
    this.ensureFoldChild(current.id);
    this.ensureFoldChild(child.id);
    this.weightCache.clear();
    this.status = `${this.nodeLabel(child)} added (${describeMatrix(action.range)})`;
    this.refresh();
  }

  async withBusy<T>(label: string, task: () => T | Promise<T>, successStatus?: string): Promise<T | undefined> {
    this.isBusy = true;
    this.busyLabel = label;
    this.refresh();

    const startedAt = performance.now();
    try {
      const result = await task();
      if (successStatus) this.status = successStatus;
      return result;
    } catch (error) {
      this.status = error instanceof Error ? error.message : "Something went wrong";
      return undefined;
    } finally {
      const elapsed = performance.now() - startedAt;
      if (elapsed < 420) {
        await wait(420 - elapsed);
      }
      this.isBusy = false;
      this.busyLabel = null;
      this.refresh();
    }
  }

  exportSnapshot(): SerializedAppState {
    return {
      schemaVersion: 1,
      settings: this.settings,
      nodes: Array.from(this.nodes.values()).map(serializeNode),
      rootId: this.rootId,
      currentId: this.currentId,
      nextSeq: this.nextSeq,
      bookmarks: this.bookmarks,
      viewMode: this.viewMode,
      autoRandom: this.autoRandom,
      rng: this.rng
    };
  }

  importSnapshot(snapshot: SerializedAppState): void {
    if (snapshot.schemaVersion !== 1) throw new Error("Unsupported save file version");

    this.settings = snapshot.settings;
    this.nodes = new Map(snapshot.nodes.map((node) => [node.id, deserializeNode(node)]));
    this.rootId = snapshot.rootId;
    this.currentId = snapshot.currentId;
    this.nextSeq = snapshot.nextSeq;
    this.bookmarks = snapshot.bookmarks;
    this.viewMode = snapshot.viewMode;
    this.autoRandom = snapshot.autoRandom;
    this.rng = snapshot.rng;
    this.weightCache.clear();
    this.status = "Tree imported";
    this.refresh();
  }

  async saveToBrowser(): Promise<void> {
    await this.withBusy("Saving tree", async () => {
      await saveLatestSnapshot(this.exportSnapshot());
    }, "Saved to this browser");
  }

  async loadFromBrowser(): Promise<void> {
    await this.withBusy("Loading tree", async () => {
      const snapshot = await loadLatestSnapshot();
      if (!snapshot) {
        this.status = "No browser save found";
        return;
      }
      this.importSnapshot(snapshot);
      this.status = "Loaded browser save";
    });
  }

  private parseActionDraft(rangeText: string, sizeText: string): ActionDraft {
    const range = matrixFromText(rangeText, this.settings.ranks);
    const normalized = sizeText.trim().toLowerCase();

    if (normalized === "x" || normalized === "call" || normalized === "check") {
      return { kind: "call", range };
    }

    const raiseTo = Number.parseFloat(normalized);
    if (!Number.isFinite(raiseTo) || raiseTo <= 0) {
      throw new Error('Raise size must be a number, or use "X" for check/call');
    }

    return { kind: "raise", range, raiseTo };
  }

  private createActionNode(parent: TreeNode, actor: string, action: ActionDraft): TreeNode | null {
    const stack = parent.stacks[actor] ?? 0;
    const putInPot = this.settings.startingStack - stack;
    const callAmount = Math.min(Math.max(parent.toCall - putInPot, 0), stack);
    let round = parent.round;
    if (this.isLastPosition(parent.position, parent.active)) round += 1;

    const stacks = { ...parent.stacks };
    const active = { ...parent.active };
    let pot = parent.pot;
    let toCall = parent.toCall;

    if (action.kind === "call") {
      stacks[actor] = Math.max(0, stack - callAmount);
      pot += callAmount;
    } else {
      const raiseTo = action.raiseTo ?? parent.toCall;
      const raiseAmount = Math.min(stack, Math.max(raiseTo - putInPot, 0));
      if (raiseAmount <= callAmount) return null;
      stacks[actor] = Math.max(0, stack - raiseAmount);
      pot += raiseAmount;
      toCall = putInPot + raiseAmount;
    }

    return {
      id: this.makeId(),
      parentId: parent.id,
      children: [],
      kind: action.kind,
      position: actor,
      round,
      pot: roundToTenth(pot),
      toCall: roundToTenth(toCall),
      stacks,
      active,
      range: cloneMatrix(action.range),
      raiseTo: action.raiseTo,
      createdAt: Date.now()
    };
  }

  private createFoldNode(parent: TreeNode, actor: string): TreeNode {
    const active = { ...parent.active, [actor]: false };
    let round = parent.round;
    if (this.isLastPosition(parent.position, parent.active)) round += 1;

    return {
      id: this.makeId(),
      parentId: parent.id,
      children: [],
      kind: "fold",
      position: actor,
      round,
      pot: parent.pot,
      toCall: parent.toCall,
      stacks: { ...parent.stacks },
      active,
      createdAt: Date.now()
    };
  }

  private ensureFoldChild(nodeId: NodeId): void {
    const node = this.nodes.get(nodeId);
    if (!node || node.children.some((childId) => this.getNode(childId).kind === "fold")) return;

    const actor = this.nextAvailablePosition(node);
    if (!actor || !this.canNodeAct(node, actor) || this.activePlayerCount(node.active) <= 1) return;

    const fold = this.createFoldNode(node, actor);
    node.children = [...node.children, fold.id];
    this.nodes.set(fold.id, fold);
    this.sortChildren(node);
  }

  private canNodeAct(node: TreeNode, actor: string): boolean {
    const stack = node.stacks[actor] ?? 0;
    const putInPot = this.settings.startingStack - stack;
    return (node.toCall > putInPot && stack >= 0) || (actor === "BB" && node.round === 1);
  }

  private nextAvailablePosition(node: TreeNode): string | null {
    const start = this.settings.positions.indexOf(node.position);
    if (start < 0) return null;

    for (let offset = 1; offset <= this.settings.positions.length; offset += 1) {
      const position = this.settings.positions[(start + offset) % this.settings.positions.length];
      if (node.active[position]) return position;
    }

    return null;
  }

  private isLastPosition(position: string, active: Record<string, boolean>): boolean {
    const index = this.settings.positions.indexOf(position);
    for (let i = index + 1; i < this.settings.positions.length; i += 1) {
      if (active[this.settings.positions[i]]) return false;
    }
    return true;
  }

  private activePlayerCount(active: Record<string, boolean>): number {
    return this.settings.positions.reduce((count, position) => count + (active[position] ? 1 : 0), 0);
  }

  private sortChildren(node: TreeNode): void {
    node.children.sort((leftId, rightId) => {
      const left = this.getNode(leftId);
      const right = this.getNode(rightId);
      if (left.kind === "fold" && right.kind !== "fold") return 1;
      if (right.kind === "fold" && left.kind !== "fold") return -1;
      return right.pot - left.pot;
    });
  }

  private deleteSubtree(nodeId: NodeId): void {
    const node = this.nodes.get(nodeId);
    if (!node) return;
    for (const childId of node.children) this.deleteSubtree(childId);
    this.nodes.delete(nodeId);
    this.bookmarks = this.bookmarks.filter((bookmark) => bookmark.nodeId !== nodeId);
    if (this.currentId === nodeId) this.currentId = node.parentId ?? this.rootId;
  }

  private getCumulativeWeights(nodeId: NodeId): Map<string, Uint8Array> {
    const cached = this.weightCache.get(nodeId);
    if (cached) return cached;

    const node = this.getNode(nodeId);
    let weights = new Map<string, Uint8Array>();
    if (node.parentId) {
      weights = new Map(this.getCumulativeWeights(node.parentId));
    }

    if (node.range && (node.kind === "raise" || node.kind === "call")) {
      const base = weights.get(node.position) ?? fullMatrix(this.settings.ranks);
      weights.set(node.position, multiplyMatrices(base, node.range));
    }

    this.weightCache.set(nodeId, weights);
    return weights;
  }

  private makeView(): EngineView {
    const currentNode = this.getCurrentNode();
    const currentChildren = currentNode.children.map((id) => this.getNode(id));
    const path = this.getPath(currentNode.id);
    const nextActor = this.nextAvailablePosition(currentNode);
    const canAct = nextActor ? this.canNodeAct(currentNode, nextActor) : false;

    return {
      version: this.version,
      settings: this.settings,
      currentNode,
      currentChildren,
      path,
      bookmarks: this.bookmarks,
      viewMode: this.viewMode,
      autoRandom: this.autoRandom,
      rng: this.rng,
      totalNodes: this.nodes.size,
      totalActions: Array.from(this.nodes.values()).filter((node) => node.kind !== "fold").length - 1,
      nextActor,
      canAct,
      matrixCells: this.buildMatrixCells(currentNode, currentChildren),
      status: this.status,
      isBusy: this.isBusy,
      busyLabel: this.busyLabel
    };
  }

  private buildMatrixCells(currentNode: TreeNode, children: TreeNode[]): RangeCellView[] {
    const ranks = this.settings.ranks;
    const actionChildren = children.filter((child) => child.range && child.kind !== "fold");
    const parentWeights = this.getCumulativeWeights(currentNode.id);
    const matrices = actionChildren.map((child) => {
      if (!child.range) return child.range;
      if (this.viewMode === "absolute") return child.range;
      const base = parentWeights.get(child.position) ?? fullMatrix(ranks);
      return multiplyMatrices(base, child.range);
    });

    const cells: RangeCellView[] = [];
    for (let row = 0; row < ranks.length; row += 1) {
      for (let col = 0; col < ranks.length; col += 1) {
        const index = comboIndex(row, col, ranks);
        const label = comboLabel(row, col, ranks);
        const segments = matrices
          .map((matrix, actionIndex) => ({
            value: matrix?.[index] ?? 0,
            color: actionChildren[actionIndex]?.kind === "call" ? CALL_COLOR : ACTION_COLORS[actionIndex % ACTION_COLORS.length]
          }))
          .filter((segment) => segment.value > 0);

        cells.push({
          label,
          background: this.cellBackground(segments),
          foreground: foregroundForSegments(segments),
          title: titleForCell(label, segments, actionChildren, matrices, index, this.rng, this.autoRandom)
        });
      }
    }

    return cells;
  }

  private cellBackground(segments: { value: number; color: string }[]): string {
    if (segments.length === 0) return FOLD_COLOR;

    if (this.autoRandom) {
      let floor = 0;
      for (const segment of segments) {
        if (this.rng > floor && this.rng <= floor + segment.value) return segment.color;
        floor += segment.value;
      }
      return FOLD_COLOR;
    }

    let cursor = 0;
    const stops: string[] = [];
    for (const segment of segments) {
      const next = Math.min(100, cursor + segment.value);
      stops.push(`${segment.color} ${cursor}% ${next}%`);
      cursor = next;
    }

    if (cursor < 100) stops.push(`${FOLD_COLOR} ${cursor}% 100%`);
    return `linear-gradient(90deg, ${stops.join(", ")})`;
  }

  private getPath(nodeId: NodeId): TreeNode[] {
    const path: TreeNode[] = [];
    let node: TreeNode | undefined = this.nodes.get(nodeId);
    while (node) {
      path.unshift(node);
      node = node.parentId ? this.nodes.get(node.parentId) : undefined;
    }
    return path;
  }

  private getCurrentNode(): TreeNode {
    return this.getNode(this.currentId);
  }

  private getNode(nodeId: NodeId): TreeNode {
    const node = this.nodes.get(nodeId);
    if (!node) throw new Error(`Missing node ${nodeId}`);
    return node;
  }

  private makeId(): NodeId {
    return `n${this.nextSeq++}`;
  }

  private refresh(clearWeightCache = false): void {
    if (clearWeightCache) this.weightCache.clear();
    this.version += 1;
    this.view = this.makeView();
    for (const listener of this.listeners) listener();
  }

  private nodeLabel(node: TreeNode): string {
    if (node.kind === "blind") return "Blinds posted";
    if (node.kind === "fold") return `${node.position} fold`;
    if (node.kind === "call") return `${node.position} check/call ${node.toCall} BB`;
    return `${node.position} raise to ${node.toCall} BB`;
  }
}

function defaultSettings(): EngineSettings {
  return {
    startingStack: 100,
    playerCount: 6,
    positions: positionsForPlayerCount(6),
    ranks: NLH_RANKS
  };
}

function serializeNode(node: TreeNode): SerializedTreeNode {
  return {
    ...node,
    children: [...node.children],
    range: node.range ? Array.from(node.range) : undefined
  };
}

function deserializeNode(node: SerializedTreeNode): TreeNode {
  return {
    ...node,
    children: [...node.children],
    range: node.range ? Uint8Array.from(node.range) : undefined
  };
}

function roundToTenth(value: number): number {
  return Math.round(value * 10) / 10;
}

function wait(ms: number): Promise<void> {
  return new Promise((resolve) => window.setTimeout(resolve, ms));
}

function foregroundForSegments(segments: { value: number; color: string }[]): string {
  if (segments.length === 0) return CELL_TEXT_DARK;
  const dominant = segments.reduce((best, segment) => (segment.value > best.value ? segment : best), segments[0]);
  return dominant.color === FOLD_COLOR ? CELL_TEXT_DARK : CELL_TEXT_LIGHT;
}

function titleForCell(
  label: string,
  segments: { value: number; color: string }[],
  actions: TreeNode[],
  matrices: (Uint8Array | undefined)[],
  index: number,
  rng: number,
  autoRandom: boolean
): string {
  if (segments.length === 0) return `${label}: fold`;

  const lines = [`${label}${autoRandom ? `, RNG ${rng}` : ""}`];
  actions.forEach((action, actionIndex) => {
    const value = matrices[actionIndex]?.[index] ?? 0;
    if (value > 0) {
      const type = action.kind === "call" ? "Call" : `Raise ${action.toCall} BB`;
      lines.push(`${action.position} ${type}: ${value}%`);
    }
  });
  return lines.join("\n");
}

export const preflopEngine = new PreflopEngine();

export function usePreflopView(): EngineView {
  return useSyncExternalStore(preflopEngine.subscribe, preflopEngine.getSnapshot, preflopEngine.getSnapshot);
}

export function actionColor(index: number, kind: string): string {
  if (kind === "call") return CALL_COLOR;
  if (kind === "fold") return FOLD_COLOR;
  return ACTION_COLORS[index % ACTION_COLORS.length];
}

export function actionLabel(node: TreeNode): string {
  if (node.kind === "blind") return "Blinds";
  if (node.kind === "fold") return `${node.position} Fold`;
  if (node.kind === "call") return `${node.position} Check/Call ${node.toCall} BB`;
  return `${node.position} Raise to ${node.toCall} BB`;
}

export function actionDensity(node: TreeNode): string {
  if (!node.range) return "";
  return `${matrixDensity(node.range).toFixed(1)}%`;
}
