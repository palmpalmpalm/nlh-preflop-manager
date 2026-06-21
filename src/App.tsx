import { useEffect } from "react";
import { HeaderBar } from "./components/HeaderBar";
import { ActionPanel } from "./components/ActionPanel";
import { RangeGrid } from "./components/RangeGrid";
import { SideRail } from "./components/SideRail";
import { TreePanel } from "./components/TreePanel";
import { preflopEngine, usePreflopView } from "./state/preflopEngine";

export function App() {
  const view = usePreflopView();

  useEffect(() => {
    const interval = window.setInterval(() => preflopEngine.rollRng(), 5000);
    return () => window.clearInterval(interval);
  }, []);

  return (
    <div className="app-shell">
      <HeaderBar view={view} />
      <main className="workspace">
        <SideRail view={view} />
        <ActionPanel view={view} />
        <RangeGrid view={view} />
        <TreePanel view={view} />
      </main>
    </div>
  );
}
