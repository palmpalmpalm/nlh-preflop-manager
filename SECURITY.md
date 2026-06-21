# Security

## Secrets

Do not commit Cloudflare API tokens, GitHub tokens, or any other deployment secrets.

Use local environment variables when deploying manually:

```bash
CLOUDFLARE_ACCOUNT_ID=<account_id> CLOUDFLARE_API_TOKEN=<token> npm run deploy:cloudflare
```

Use Cloudflare Pages project settings or GitHub Actions secrets for automated deploys.

If a token is pasted into chat, terminal output, or an issue, rotate it in Cloudflare and replace it with a new scoped token.

## Recommended Cloudflare Token Scope

Use the smallest scope needed for Pages deployment and custom-domain management:

- Account: Cloudflare Pages: Edit
- Zone: DNS: Edit
- Zone: Zone: Read

Keep the token limited to the account and zone used by `donkjam.com`.
