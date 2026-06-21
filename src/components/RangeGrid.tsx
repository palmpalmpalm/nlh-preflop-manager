import { Shuffle, Sigma } from "lucide-react";
import type { EngineView } from "../core/types";
import { preflopEngine } from "../state/preflopEngine";

interface RangeGridProps {
  view: EngineView;
}

export function RangeGrid({ view }: RangeGridProps) {
  const gridStyle = {
    gridTemplateColumns: `repeat(${view.settings.ranks.length}, var(--cell))`
  };

  return (
    <section className="range-board">
      <div className="range-toolbar">
        <div>
          <span className="eyebrow">Combos</span>
          <h1>{view.currentNode.position} Node</h1>
        </div>
        <div className="segmented">
          <button
            className={view.viewMode === "absolute" ? "active" : ""}
            onClick={() => preflopEngine.setViewMode("absolute")}
          >
            Absolute
          </button>
          <button
            className={view.viewMode === "weighted" ? "active" : ""}
            onClick={() => preflopEngine.setViewMode("weighted")}
          >
            <Sigma size={15} />
            Weighted
          </button>
        </div>
        <button
          className={`tool-button ${view.autoRandom ? "accent" : ""}`}
          onClick={() => preflopEngine.setAutoRandom(!view.autoRandom)}
          title="Toggle randomizer"
        >
          <Shuffle size={17} />
          <span>{view.autoRandom ? "Random On" : "Random Off"}</span>
        </button>
      </div>

      <div className="combo-grid" style={gridStyle}>
        {view.matrixCells.map((cell) => (
          <div
            key={cell.label}
            className="combo-cell"
            title={cell.title}
            style={{ background: cell.background, color: cell.foreground }}
          >
            {cell.label}
          </div>
        ))}
      </div>
    </section>
  );
}
