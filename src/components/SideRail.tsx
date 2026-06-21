import { X } from "lucide-react";
import type { EngineView } from "../core/types";
import { preflopEngine } from "../state/preflopEngine";

interface SideRailProps {
  view: EngineView;
}

export function SideRail({ view }: SideRailProps) {
  return (
    <aside className="side-rail">
      <div className="rail-title">Hot Keys</div>
      <div className="rail-list">
        {view.bookmarks.map((bookmark) => (
          <div className="bookmark-row" key={bookmark.id}>
            <button onClick={() => preflopEngine.navigate(bookmark.nodeId)}>{bookmark.label}</button>
            <button className="icon-button danger" onClick={() => preflopEngine.removeBookmark(bookmark.id)} title="Remove">
              <X size={14} />
            </button>
          </div>
        ))}
      </div>
    </aside>
  );
}
