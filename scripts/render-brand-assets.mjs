import { mkdirSync, readFileSync, writeFileSync } from "node:fs";
import { dirname, resolve } from "node:path";
import { spawnSync } from "node:child_process";

const ROOT = resolve(import.meta.dirname, "..");
const COLORS = {
  background: "#fbf8f0",
  panel: "#fffefa",
  border: "#23282d",
  orange: "#ff4f1f",
  green: "#72f000",
  gray: "#d4d8dd",
  text: "#171a1f",
  muted: "#4d5661",
  teal: "#009b75"
};

const pattern = [
  "OOOOOOOSGOGOG",
  "OOOOOOSGGGGSG",
  "OOOOOSGGGGGGG",
  "OOOOOSGOGGGGG",
  "SSGGOSOSGGGGG",
  "SGGGGOSOOSGGG",
  "GGGGGGOOOGGGG",
  "GGGGGGGOOOSGG",
  "GGGXXGGGOOSGG",
  "GGGXXXGGGOGSG",
  "GGXXXXXGGGOGG",
  "GGXXXXXXGGGOG",
  "GXXXXXXXXGGGO"
];

function ensureDir(path) {
  mkdirSync(dirname(path), { recursive: true });
}

function esc(text) {
  return text.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
}

function rangeGridSvg() {
  const iconSize = 512;
  const gridX = 96;
  const gridY = 96;
  const cell = 20;
  const gap = 5;
  const radius = 4.5;
  const cells = [];

  for (let row = 0; row < pattern.length; row += 1) {
    for (let col = 0; col < pattern[row].length; col += 1) {
      const code = pattern[row][col];
      const x = gridX + col * (cell + gap);
      const y = gridY + row * (cell + gap);
      const clipId = `cell-${row}-${col}`;

      if (code === "S") {
        cells.push(`
          <clipPath id="${clipId}"><rect x="${x}" y="${y}" width="${cell}" height="${cell}" rx="${radius}"/></clipPath>
          <rect x="${x}" y="${y}" width="${cell}" height="${cell}" rx="${radius}" fill="${COLORS.orange}"/>
          <rect x="${x + cell * 0.44}" y="${y}" width="${cell * 0.56}" height="${cell}" fill="${COLORS.green}" clip-path="url(#${clipId})"/>`);
      } else {
        const fill = code === "O" ? COLORS.orange : code === "G" ? COLORS.green : COLORS.gray;
        cells.push(`<rect x="${x}" y="${y}" width="${cell}" height="${cell}" rx="${radius}" fill="${fill}"/>`);
      }
    }
  }

  return `<svg xmlns="http://www.w3.org/2000/svg" width="${iconSize}" height="${iconSize}" viewBox="0 0 ${iconSize} ${iconSize}">
  <defs>
    <filter id="softShadow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="12" stdDeviation="16" flood-color="#101820" flood-opacity="0.12"/>
    </filter>
  </defs>
  <rect x="28" y="28" width="456" height="456" rx="92" fill="${COLORS.background}"/>
  <rect x="72" y="72" width="368" height="368" rx="48" fill="${COLORS.panel}" stroke="${COLORS.border}" stroke-width="13" filter="url(#softShadow)"/>
  <g>${cells.join("")}
  </g>
</svg>`;
}

function maskSvg() {
  const gridX = 96;
  const gridY = 96;
  const cell = 20;
  const gap = 5;
  const cells = [];

  for (let row = 0; row < pattern.length; row += 1) {
    for (let col = 0; col < pattern[row].length; col += 1) {
      if (pattern[row][col] === "X") continue;
      const x = gridX + col * (cell + gap);
      const y = gridY + row * (cell + gap);
      cells.push(`<rect x="${x}" y="${y}" width="${cell}" height="${cell}" rx="4.5"/>`);
    }
  }

  return `<svg xmlns="http://www.w3.org/2000/svg" width="512" height="512" viewBox="0 0 512 512">
  <rect x="72" y="72" width="368" height="368" rx="48" fill="#000"/>
  <rect x="88" y="88" width="336" height="336" rx="34" fill="#fff"/>
  <g fill="#000">${cells.join("")}</g>
</svg>`;
}

function ogCardSvg() {
  const icon = readFileSync(resolve(ROOT, "public/icon-512.png")).toString("base64");
  const title = "Build, browse, and";
  const titleAccent = "preflop ranges.";
  const descriptionA = "A fast local-first web app for No-Limit Hold'em";
  const descriptionB = "game trees, weighted combos, and PC-first";
  const descriptionC = "range work.";

  return `<svg xmlns="http://www.w3.org/2000/svg" width="1200" height="630" viewBox="0 0 1200 630">
  <rect width="1200" height="630" fill="#f7f8f8"/>
  <path d="M0 0H390C300 150 249 326 293 630H0Z" fill="${COLORS.orange}" opacity="0.08"/>
  <circle cx="1046" cy="88" r="240" fill="#02a078" opacity="0.08"/>
  <rect x="64" y="160" width="320" height="320" rx="48" fill="#ffffff" stroke="#d6dde3"/>
  <image href="data:image/png;base64,${icon}" x="94" y="190" width="260" height="260" preserveAspectRatio="xMidYMid meet"/>
  <text x="456" y="87" fill="#68717c" font-family="Arial, Helvetica, sans-serif" font-size="25" font-weight="700">NLH Preflop Manager</text>
  <text x="456" y="180" fill="${COLORS.text}" font-family="Arial, Helvetica, sans-serif" font-size="76" font-weight="900">${esc(title)}</text>
  <text x="456" y="253" fill="${COLORS.text}" font-family="Arial, Helvetica, sans-serif" font-size="76" font-weight="900">randomize</text>
  <text x="456" y="326" fill="${COLORS.orange}" font-family="Arial, Helvetica, sans-serif" font-size="76" font-weight="900">${esc(titleAccent)}</text>
  <text x="456" y="390" fill="${COLORS.muted}" font-family="Arial, Helvetica, sans-serif" font-size="30" font-weight="700">${esc(descriptionA)}</text>
  <text x="456" y="427" fill="${COLORS.muted}" font-family="Arial, Helvetica, sans-serif" font-size="30" font-weight="700">${esc(descriptionB)}</text>
  <text x="456" y="464" fill="${COLORS.muted}" font-family="Arial, Helvetica, sans-serif" font-size="30" font-weight="700">${esc(descriptionC)}</text>
  <g font-family="Arial, Helvetica, sans-serif" font-size="23" font-weight="800" fill="${COLORS.text}">
    <rect x="456" y="506" width="149" height="54" rx="27" fill="#ffffff" stroke="#d4dce3"/>
    <text x="476" y="541">13x13 grid</text>
    <rect x="620" y="506" width="201" height="54" rx="27" fill="#ffffff" stroke="#d4dce3"/>
    <text x="640" y="541">Weighted mode</text>
    <rect x="836" y="506" width="87" height="54" rx="27" fill="#ffffff" stroke="#d4dce3"/>
    <text x="856" y="541">PWA</text>
  </g>
  <text x="456" y="619" fill="${COLORS.teal}" font-family="Arial, Helvetica, sans-serif" font-size="24" font-weight="800">preflop.donkjam.com</text>
</svg>`;
}

function render(svgPath, outPath, width, height = width) {
  ensureDir(outPath);
  const result = spawnSync(
    "sips",
    ["-s", "format", "png", "-z", String(height), String(width), svgPath, "--out", outPath],
    { cwd: ROOT, encoding: "utf8" }
  );

  if (result.status !== 0) {
    throw new Error(`sips failed for ${outPath}\n${result.stderr || result.stdout}`);
  }
}

const iconSvgPath = resolve(ROOT, "assets/brand/icon-range-grid.svg");
const maskSvgPath = resolve(ROOT, "public/mask-icon.svg");
const ogSvgPath = resolve(ROOT, "assets/brand/og-card.svg");

ensureDir(iconSvgPath);
writeFileSync(iconSvgPath, rangeGridSvg());
writeFileSync(maskSvgPath, maskSvg());

render(iconSvgPath, resolve(ROOT, "public/icon-512.png"), 512);
render(iconSvgPath, resolve(ROOT, "public/icon-192.png"), 192);
render(iconSvgPath, resolve(ROOT, "public/apple-touch-icon.png"), 180);
render(iconSvgPath, resolve(ROOT, "public/favicon-64.png"), 64);
render(iconSvgPath, resolve(ROOT, "public/favicon-32.png"), 32);
render(iconSvgPath, resolve(ROOT, "public/favicon-16.png"), 16);

writeFileSync(ogSvgPath, ogCardSvg());
render(ogSvgPath, resolve(ROOT, "public/og-image.png"), 1200, 630);
render(ogSvgPath, resolve(ROOT, "public/og-image-v2.png"), 1200, 630);

console.log("Rendered brand assets from assets/brand/icon-range-grid.svg");
