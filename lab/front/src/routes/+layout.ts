// The whole app is a static SPA: every route is prerendered at build time into
// plain HTML/CSS/JS, and all data comes from the backend REST API via fetch()
// in the browser at runtime. No SvelteKit server-side rendering/data loading
// is used, so this works with `adapter-static` and any plain static host.
export const prerender = true;
export const ssr = true;
export const trailingSlash = 'always';
