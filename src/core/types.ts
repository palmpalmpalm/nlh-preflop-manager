export type NodeId = string;
export type ActionKind = "blind" | "raise" | "call" | "fold";
export type ViewMode = "absolute" | "weighted";

export interface EngineSettings {
  startingStack: number;
  playerCount: 2 | 6;
  positions: string[];
  ranks: string;
}

export interface TreeNode {
  id: NodeId;
  parentId: NodeId | null;
  children: NodeId[];
  kind: ActionKind;
  position: string;
  round: number;
  pot: number;
  toCall: number;
  stacks: Record<string, number>;
  active: Record<string, boolean>;
  range?: Uint8Array;
  raiseTo?: number;
  createdAt: number;
}

export interface Bookmark {
  id: string;
  label: string;
  nodeId: NodeId;
}

export interface SerializedTreeNode extends Omit<TreeNode, "range" | "children"> {
  children: NodeId[];
  range?: number[];
}

export interface SerializedAppState {
  schemaVersion: 1;
  settings: EngineSettings;
  nodes: SerializedTreeNode[];
  rootId: NodeId;
  currentId: NodeId;
  nextSeq: number;
  bookmarks: Bookmark[];
  viewMode: ViewMode;
  autoRandom: boolean;
  rng: number;
}

export interface RangeCellView {
  label: string;
  background: string;
  foreground: string;
  title: string;
}

export interface EngineView {
  version: number;
  settings: EngineSettings;
  currentNode: TreeNode;
  currentChildren: TreeNode[];
  path: TreeNode[];
  bookmarks: Bookmark[];
  viewMode: ViewMode;
  autoRandom: boolean;
  rng: number;
  totalNodes: number;
  totalActions: number;
  nextActor: string | null;
  canAct: boolean;
  matrixCells: RangeCellView[];
  status: string;
  isBusy: boolean;
  busyLabel: string | null;
}
