# NLH Preflop Manager Web

Modern web rebuild of the original JavaFX NLH Preflop Manager.

Live app: https://preflop.donkjam.com

## What It Does

- Builds No-Limit Hold'em preflop action trees.
- Edits 13x13 range grids with absolute, weighted, and random views.
- Keeps work local-first in the browser with IndexedDB saves.
- Imports and exports portable JSON strategy files.
- Runs as a responsive PWA on desktop, tablet, and mobile.

## Local Development

```bash
npm install
npm run dev
```

The local dev server defaults to `http://localhost:5173/`.

## Quality Checks

```bash
npm run typecheck
npm run build
npm run preview
```

`npm run build` writes the static app to `dist/`.

## User Workflow

1. Use the tree panel to create or select the active preflop node.
2. Use the action panel to add available actions and tune their frequencies.
3. Edit the center 13x13 grid to define combo strategy for the selected node.
4. Toggle between absolute and weighted views when checking range composition.
5. Use random mode for weighted spot checks.
6. Save locally during study sessions, or export JSON when moving work between machines.

See `docs/USER_GUIDE.md` for a fuller operator guide.

## Save Model

- `Save` stores the latest tree in browser IndexedDB.
- `Load` restores the IndexedDB save.
- `Export` downloads a portable JSON tree.
- `Import` restores a JSON tree.

## Project Structure

```text
src/components/      UI panels and virtualized lists
src/core/            card, range, storage, and type helpers
src/state/           preflop tree engine and derived range state
public/              PWA icons, favicon, social image, robots, sitemap
assets/brand/        source brand artwork
```

## SEO And Social Sharing

Production metadata lives in `index.html`, `public/manifest.webmanifest`, `public/robots.txt`, and `public/sitemap.xml`.

The primary social preview image is:

```text
public/og-image-v2.png
```

Brand icons are generated from:

```text
assets/brand/icon-range-grid.svg
```

Regenerate favicons, app icons, the mask icon, and the social preview image with:

```bash
npm run brand:render
```

The generator also writes `public/og-image.png` as the latest unversioned copy. Metadata should point at a versioned `og-image-v*.png` file when visual changes need to bypass social-card caches.

If the domain changes, update the canonical URL, Open Graph URL/image, Twitter image, JSON-LD URL, robots sitemap URL, and sitemap location together.

## Deploy

This app is deployed on Cloudflare Pages:

```text
Project: donkjam-preflop
Domain: preflop.donkjam.com
```

Manual deploy from this machine:

```bash
CLOUDFLARE_ACCOUNT_ID=<account_id> CLOUDFLARE_API_TOKEN=<token> npm run deploy:cloudflare
```

Do not commit real tokens. Copy `.env.example` if you want local shell helpers.

It also builds to static files, so it can run on Vercel, Netlify, GitHub Pages, or any static host. Use `dist` as the publish/output directory.

## Performance Notes

- The game tree is stored as a normalized `Map<NodeId, TreeNode>`, not recursive React state.
- React subscribes to a small external-store snapshot with `useSyncExternalStore`.
- Each action stores only its own `Uint8Array` range matrix.
- Weighted ranges are derived for the active path only and cached by node id.
- Action and path lists are virtualized, so large branches do not render every row.
- The combo grid renders one fixed-size element per hand and uses CSS gradients for action frequency bars.
