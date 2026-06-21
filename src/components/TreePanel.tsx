import type { EngineView, TreeNode } from "../core/types";
import { actionLabel, preflopEngine } from "../state/preflopEngine";
import { VirtualList } from "./VirtualList";

interface TreePanelProps {
  view: EngineView;
}

export function TreePanel({ view }: TreePanelProps) {
  return (
    <section className="panel tree-panel">
      <div className="panel-heading">
        <div>
          <span className="eyebrow">Game Tree</span>
          <h2>{view.totalNodes.toLocaleString()} nodes</h2>
        </div>
      </div>

      <div className="stat-grid">
        <Stat label="Pot" value={`${view.currentNode.pot} BB`} />
        <Stat label="To Call" value={`${view.currentNode.toCall} BB`} />
        <Stat label="Round" value={String(view.currentNode.round)} />
        <Stat label="Actions" value={view.totalActions.toLocaleString()} />
      </div>

      <div className="subsection">
        <span className="eyebrow">Path</span>
        <VirtualList
          className="path-list"
          itemHeight={38}
          items={view.path}
          renderItem={(node) => <PathButton key={node.id} node={node} />}
        />
      </div>

      <div className="subsection">
        <span className="eyebrow">Stacks</span>
        <div className="stack-table">
          {view.settings.positions.map((position) => (
            <div className={!view.currentNode.active[position] ? "inactive" : ""} key={position}>
              <span>{position}</span>
              <strong>{view.currentNode.stacks[position]?.toFixed(1)}</strong>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}

function Stat({ label, value }: { label: string; value: string }) {
  return (
    <div className="stat">
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}

function PathButton({ node }: { node: TreeNode }) {
  return (
    <button className="path-button" onClick={() => preflopEngine.navigate(node.id)}>
      {actionLabel(node)}
    </button>
  );
}
