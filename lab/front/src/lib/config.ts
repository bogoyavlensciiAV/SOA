/**
 * Single point of configuration for the app.
 *
 * The API base URL is read from the `PUBLIC_API_BASE_URL` environment
 * variable at BUILD TIME (Vite inlines `import.meta.env.*` values into the
 * static JS bundle, since this app has no Node.js server at runtime).
 *
 * To configure it, create a `.env` file (or `.env.production`) in the
 * project root before running `npm run build`:
 *
 *   PUBLIC_API_BASE_URL=https://api.example.com
 *
 * See the README for details.
 */

// `$env/static/public` is resolved and INLINED at build time (works with a
// fully static, server-less build, unlike `$env/dynamic/public`).
import { PUBLIC_API_BASE_URL } from '$env/static/public';

// Fall back to localhost during local development if nothing is set,
// so the app doesn't silently call an empty base URL.
export const API_BASE_URL: string =
	(PUBLIC_API_BASE_URL && PUBLIC_API_BASE_URL.trim()) || 'http://localhost:8080';

// Default page size, matching the API's documented default.
export const DEFAULT_PAGE_SIZE = 25;
