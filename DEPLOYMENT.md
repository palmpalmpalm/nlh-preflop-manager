# Deployment

The app is a static Vite/PWA build. There is no server-side runtime requirement.

Live app:

```text
https://preflop.donkjam.com
```

Cloudflare Pages project:

```text
donkjam-preflop
```

## Recommended First Deploy

Use Cloudflare Pages first.

This project is a static Vite/PWA app with browser-local persistence, so Cloudflare Pages is a clean fit: fast global static hosting, simple GitHub deploys, no server runtime to manage, and the app can stay inexpensive while you validate real users.

Vercel is also good if you already use it or expect to add a backend/API later. Netlify is good if you prefer its form, redirect, and static-site workflow. For this app today, Cloudflare Pages is the simplest default.

Build command:

```bash
npm run build
```

Output directory:

```text
dist
```

Cloudflare Pages settings:

```text
Build command: npm run build
Build output directory: dist
Custom domain: preflop.donkjam.com
```

Manual deploy from this machine:

```bash
CLOUDFLARE_ACCOUNT_ID=<account_id> CLOUDFLARE_API_TOKEN=<token> npm run deploy:cloudflare
```

Do not commit Cloudflare tokens. Rotate any token that was shared in chat or logs.

## Custom Domain Checklist

For `preflop.donkjam.com`:

1. Add the custom domain in the Cloudflare Pages project.
2. Create a CNAME from `preflop` to the Pages hostname shown by Cloudflare.
3. Wait until the Pages custom-domain status is active.
4. Verify the production URL:

```bash
curl -I https://preflop.donkjam.com
```

## SEO Checklist

Before a production deploy, keep these files aligned with the live domain:

- `index.html`
- `public/manifest.webmanifest`
- `public/robots.txt`
- `public/sitemap.xml`
- `public/og-image.png`

Use the social image size `1200x630` for reliable Open Graph and Twitter card previews. Point metadata at a versioned filename such as `og-image-v2.png` when artwork changes, because social platforms and CDNs can cache preview images aggressively.

## Routing

The app is currently a single-page app. Vercel and Netlify configs are included so direct reloads route back to `index.html`.
Cloudflare Pages reads `public/_redirects`, which provides the same SPA fallback.

## Offline/PWA

The service worker caches the app shell and fetched build assets. When changing the service worker caching strategy, bump `CACHE_NAME` in `public/sw.js`.

## Static Headers

Cloudflare Pages reads `public/_headers` during build. It sets long-lived caching for hashed assets and conservative headers for HTML/PWA shell files.

## Desktop Packaging Later

If users strongly prefer a downloadable Windows app, wrap the same web build with Tauri or Electron after the browser UX stabilizes.

Tauri is the lighter default for this project. Electron is useful if the app eventually needs deeper Node integrations, global shortcuts, native menus, auto-update flows, or poker-client-adjacent desktop behavior.
