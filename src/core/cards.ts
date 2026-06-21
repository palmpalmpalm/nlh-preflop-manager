export const NLH_RANKS = "AKQJT98765432";
export const SHORT_DECK_RANKS = "AKQJT9876";

export function positionsForPlayerCount(playerCount: 2 | 6): string[] {
  return playerCount === 2 ? ["SB", "BB"] : ["LJ", "HJ", "CO", "BU", "SB", "BB"];
}

export function matrixLength(ranks: string): number {
  return ranks.length * ranks.length;
}

export function comboIndex(row: number, col: number, ranks: string): number {
  return row * ranks.length + col;
}

export function comboLabel(row: number, col: number, ranks: string): string {
  if (row === col) return `${ranks[row]}${ranks[col]}`;
  if (row < col) return `${ranks[row]}${ranks[col]}s`;
  return `${ranks[col]}${ranks[row]}o`;
}

export function normalizeComboToken(token: string): string {
  const trimmed = token.trim().replace(/10/g, "T").replace(/ /g, "");
  if (trimmed.length <= 2) return trimmed.toUpperCase();

  const cards = trimmed.slice(0, 2).toUpperCase();
  const suffix = trimmed.slice(2).toLowerCase();
  return `${cards}${suffix}`;
}

export function comboPosition(combo: string, ranks: string): [number, number] {
  const normalized = normalizeComboToken(combo);
  const first = ranks.indexOf(normalized[0]);
  const second = ranks.indexOf(normalized[1]);

  if (first < 0 || second < 0) {
    throw new Error(`Unknown combo "${combo}" for ranks ${ranks}`);
  }

  if (normalized.length === 2) {
    if (first !== second) throw new Error(`Combo "${combo}" needs "s" or "o" suffix`);
    return [first, second];
  }

  if (normalized[2] === "o") return [second, first];
  if (normalized[2] === "s") return [first, second];

  throw new Error(`Combo "${combo}" needs "s" or "o" suffix`);
}
