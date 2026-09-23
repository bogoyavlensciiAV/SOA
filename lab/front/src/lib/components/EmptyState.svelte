<script lang="ts">
	let {
		title = 'Ничего не найдено',
		description = 'Попробуйте изменить условия фильтрации.'
	}: { title?: string; description?: string } = $props();
</script>

<div class="flex flex-col items-center justify-center gap-2 py-16 text-center">
	<svg class="h-10 w-10 text-slate-300" viewBox="0 0 24 24" fill="none">
		<rect x="3.5" y="6.5" width="17" height="13" rx="2" stroke="currentColor" stroke-width="1.4" />
		<path d="M3.5 10.5h17" stroke="currentColor" stroke-width="1.4" />
		<path d="M8 3.5v4M16 3.5v4" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" />
	</svg>
	<p class="text-sm font-medium text-slate-700">{title}</p>
	<p class="max-w-xs text-sm text-slate-500">{description}</p>
</div>
