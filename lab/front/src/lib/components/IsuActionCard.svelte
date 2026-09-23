<script lang="ts">
	import type { Snippet } from 'svelte';

	let {
		title,
		description,
		children
	}: { title: string; description?: string; children: Snippet } = $props();
</script>

<section class="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
	<h2 class="text-base font-semibold text-slate-900">{title}</h2>
	{#if description}
		<p class="mt-1 text-sm text-slate-500">{description}</p>
	{/if}
	<div class="mt-4">
		{@render children()}
	</div>
</section>
