import { comboIndex, comboLabel, comboPosition, matrixLength, normalizeComboToken } from "./cards";

export function emptyMatrix(ranks: string): Uint8Array {
  return new Uint8Array(matrixLength(ranks));
}

export function fullMatrix(ranks: string): Uint8Array {
  const matrix = new Uint8Array(matrixLength(ranks));
  matrix.fill(100);
  return matrix;
}

export function cloneMatrix(matrix: Uint8Array): Uint8Array {
  return new Uint8Array(matrix);
}

export function multiplyMatrices(left: Uint8Array, right: Uint8Array): Uint8Array {
  const out = new Uint8Array(left.length);
  for (let i = 0; i < left.length; i += 1) {
    out[i] = Math.floor((left[i] * right[i]) / 100);
  }
  return out;
}

export function matrixDensity(matrix: Uint8Array): number {
  let total = 0;
  for (const value of matrix) total += value;
  return total / matrix.length;
}

export function matrixFromText(text: string, ranks: string): Uint8Array {
  const matrix = emptyMatrix(ranks);
  const tokens = text
    .split(/[,\n]+/)
    .map((token) => token.trim())
    .filter(Boolean);

  if (tokens.length === 0) throw new Error("Enter at least one combo, for example: AA,AKs,AQo");

  for (const rawToken of tokens) {
    const { token, weight } = extractWeight(rawToken);
    addRangeToken(matrix, token, weight, ranks);
  }

  return matrix;
}

export function describeMatrix(matrix: Uint8Array): string {
  const density = matrixDensity(matrix);
  return `${density.toFixed(1)}% avg frequency`;
}

function extractWeight(rawToken: string): { token: string; weight: number } {
  const match = rawToken.match(/^\[(\d+(?:\.\d+)?)(?:%)?\](.+)$/);
  if (!match) return { token: rawToken, weight: 100 };

  const weight = Number.parseFloat(match[1]);
  if (!Number.isFinite(weight) || weight < 0 || weight > 100) {
    throw new Error(`Invalid weight in "${rawToken}"`);
  }

  return { token: match[2], weight: Math.floor(weight) };
}

function addRangeToken(matrix: Uint8Array, rawToken: string, weight: number, ranks: string): void {
  const token = normalizeComboToken(rawToken);

  if (token.includes("-")) {
    const [start, end] = token.split("-");
    addHyphenRange(matrix, start, end, weight, ranks);
    return;
  }

  if (token.endsWith("+")) {
    addPlusRange(matrix, token.slice(0, -1), weight, ranks);
    return;
  }

  const [row, col] = comboPosition(token, ranks);
  matrix[comboIndex(row, col, ranks)] = weight;
}

function addHyphenRange(
  matrix: Uint8Array,
  start: string,
  end: string,
  weight: number,
  ranks: string
): void {
  let [row, col] = comboPosition(start, ranks);
  const [endRow, endCol] = comboPosition(end, ranks);
  let guard = 0;
  const maxSteps = ranks.length * ranks.length;

  while ((row !== endRow || col !== endCol) && guard < maxSteps) {
    matrix[comboIndex(row, col, ranks)] = weight;
    [row, col] = stepTowardRangeEnd(row, col);
    guard += 1;
  }

  if (guard >= maxSteps) throw new Error(`Invalid range "${start}-${end}"`);
  matrix[comboIndex(endRow, endCol, ranks)] = weight;
}

function stepTowardRangeEnd(row: number, col: number): [number, number] {
  if (row === col) return [row + 1, col + 1];
  if (col < row) return [row + 1, col];
  return [row, col + 1];
}

function addPlusRange(matrix: Uint8Array, combo: string, weight: number, ranks: string): void {
  const [row, col] = comboPosition(combo, ranks);
  const gap = Math.abs(row - col);

  if (row === col) {
    for (let i = 0; i <= row; i += 1) {
      matrix[comboIndex(i, i, ranks)] = weight;
    }
    return;
  }

  if (gap === 1) {
    for (let i = row; i >= 0; i -= 1) {
      const j = col < row ? i - gap : i + gap;
      if (j >= 0 && j < ranks.length) matrix[comboIndex(i, j, ranks)] = weight;
    }
    return;
  }

  if (row < col) {
    for (let j = row + 1; j <= col; j += 1) {
      matrix[comboIndex(row, j, ranks)] = weight;
    }
    return;
  }

  for (let i = col + 1; i <= row; i += 1) {
    matrix[comboIndex(i, col, ranks)] = weight;
  }
}

export function matrixToDebugRows(matrix: Uint8Array, ranks: string): string[] {
  const rows: string[] = [];
  for (let row = 0; row < ranks.length; row += 1) {
    const values: string[] = [];
    for (let col = 0; col < ranks.length; col += 1) {
      const value = matrix[comboIndex(row, col, ranks)];
      if (value > 0) values.push(`${comboLabel(row, col, ranks)}:${value}`);
    }
    rows.push(values.join(" "));
  }
  return rows;
}
