<script lang="ts">
	import type { Snippet } from 'svelte';

	let {
		title,
		children
	}: { title: string; children: Snippet } = $props();
</script>

<section class="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
	<h2 class="text-sm font-semibold text-slate-900">{title}</h2>
	<div class="mb-4">
		{@render children()}
	</div>
</section>
