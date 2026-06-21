import { ChangeEvent, useRef, useState } from "react";
import { Download, HardDrive, Play, RotateCcw, Save, Upload } from "lucide-react";
import type { EngineView, SerializedAppState } from "../core/types";
import { preflopEngine } from "../state/preflopEngine";

interface HeaderBarProps {
  view: EngineView;
}

export function HeaderBar({ view }: HeaderBarProps) {
  const [stack, setStack] = useState(String(view.settings.startingStack));
  const [players, setPlayers] = useState<2 | 6>(view.settings.playerCount);
  const fileInput = useRef<HTMLInputElement | null>(null);

  function createTree() {
    const parsed = Number.parseFloat(stack);
    preflopEngine.createNewTree(players, Number.isFinite(parsed) && parsed > 0 ? parsed : 100);
  }

  async function importFile(event: ChangeEvent<HTMLInputElement>) {
    const file = event.currentTarget.files?.[0];
    if (!file) return;

    await preflopEngine.withBusy("Reading file", async () => {
      const text = await file.text();
      preflopEngine.importSnapshot(JSON.parse(text) as SerializedAppState);
    }, "Tree imported");
    event.currentTarget.value = "";
  }

  async function exportFile() {
    await preflopEngine.withBusy("Preparing export", () => {
      const snapshot = preflopEngine.exportSnapshot();
      const blob = new Blob([JSON.stringify(snapshot)], { type: "application/json" });
      const url = URL.createObjectURL(blob);
      const anchor = document.createElement("a");
      anchor.href = url;
      anchor.download = `nlh-preflop-${new Date().toISOString().slice(0, 10)}.json`;
      anchor.click();
      URL.revokeObjectURL(url);
    }, "Export ready");
  }

  return (
    <header className="header-bar" aria-busy={view.isBusy}>
      {view.isBusy && <div className="loading-line" />}
      <button className="brand-button" onClick={() => preflopEngine.navigateRoot()} title="Go to root">
        <span className="brand-mark">PM</span>
        <span>
          <strong>NLH Preflop Manager</strong>
          <small>{view.isBusy ? view.busyLabel : view.status}</small>
        </span>
        {view.isBusy && (
          <span className="busy-pill">
            <span className="busy-spinner" />
            {view.busyLabel}
          </span>
        )}
      </button>

      <div className="header-controls">
        <label className="field compact">
          <span>Stack</span>
          <input value={stack} onChange={(event) => setStack(event.target.value)} inputMode="decimal" />
        </label>
        <label className="field compact">
          <span>Players</span>
          <select value={players} onChange={(event) => setPlayers(Number(event.target.value) as 2 | 6)}>
            <option value={6}>6-max</option>
            <option value={2}>HU</option>
          </select>
        </label>
        <button className="tool-button primary" onClick={createTree} title="New tree">
          <RotateCcw size={17} />
          <span>New</span>
        </button>
      </div>

      <div className="header-controls">
        <button
          className="tool-button"
          disabled={view.isBusy}
          onClick={() => void preflopEngine.saveToBrowser()}
          title="Save to browser"
        >
          <Save size={17} />
          <span>Save</span>
        </button>
        <button
          className="tool-button"
          disabled={view.isBusy}
          onClick={() => void preflopEngine.loadFromBrowser()}
          title="Load browser save"
        >
          <HardDrive size={17} />
          <span>Load</span>
        </button>
        <button className="tool-button" disabled={view.isBusy} onClick={() => void exportFile()} title="Export JSON">
          <Download size={17} />
          <span>Export</span>
        </button>
        <button
          className="tool-button"
          disabled={view.isBusy}
          onClick={() => fileInput.current?.click()}
          title="Import JSON"
        >
          <Upload size={17} />
          <span>Import</span>
        </button>
        <button className="tool-button accent" onClick={() => preflopEngine.rollRng()} title="Roll RNG">
          <Play size={17} />
          <span>RNG {view.rng}</span>
        </button>
        <input ref={fileInput} className="hidden-file" type="file" accept="application/json" onChange={importFile} />
      </div>
    </header>
  );
}
