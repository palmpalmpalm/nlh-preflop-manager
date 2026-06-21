import { FormEvent, useState } from "react";
import { BookmarkPlus, Plus, Trash2 } from "lucide-react";
import type { EngineView, TreeNode } from "../core/types";
import { actionColor, actionDensity, actionLabel, preflopEngine } from "../state/preflopEngine";
import { VirtualList } from "./VirtualList";

interface ActionPanelProps {
  view: EngineView;
}

export function ActionPanel({ view }: ActionPanelProps) {
  const [rangeText, setRangeText] = useState("AA,AKs,AQo");
  const [sizeText, setSizeText] = useState("2.5");

  function submit(event: FormEvent) {
    event.preventDefault();
    preflopEngine.addAction(rangeText, sizeText);
  }

  return (
    <section className="panel action-panel">
      <div className="panel-heading">
        <div>
          <span className="eyebrow">Next Action</span>
          <h2>{view.nextActor ?? "Closed"}</h2>
        </div>
        <button className="icon-button" onClick={() => preflopEngine.addBookmark()} title="Bookmark current node">
          <BookmarkPlus size={18} />
        </button>
      </div>

      <VirtualList
        className="action-list"
        itemHeight={54}
        items={view.currentChildren}
        renderItem={(node, index) => <ActionRow key={node.id} node={node} index={index} />}
      />

      <form className="add-action-form" onSubmit={submit}>
        <label className="field">
          <span>Combos</span>
          <textarea value={rangeText} onChange={(event) => setRangeText(event.target.value)} rows={3} />
        </label>
        <label className="field">
          <span>Size</span>
          <input value={sizeText} onChange={(event) => setSizeText(event.target.value)} />
        </label>
        <button className="tool-button primary wide" disabled={!view.canAct} type="submit">
          <Plus size={17} />
          <span>Add Action</span>
        </button>
      </form>
    </section>
  );
}

function ActionRow({ node, index }: { node: TreeNode; index: number }) {
  return (
    <div className="action-row">
      <button className="action-select" onClick={() => preflopEngine.navigate(node.id)} title={actionLabel(node)}>
        <span className="color-swatch" style={{ background: actionColor(index, node.kind) }} />
        <span className="action-text">
          <strong>{actionLabel(node)}</strong>
          <small>{node.kind === "fold" ? "terminal branch" : actionDensity(node)}</small>
        </span>
      </button>
      {node.kind !== "fold" && (
        <button className="icon-button danger" onClick={() => preflopEngine.deleteChild(node.id)} title="Delete action">
          <Trash2 size={16} />
        </button>
      )}
    </div>
  );
}
