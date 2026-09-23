<script lang="ts">
	let {
		message = 'Не удалось загрузить данные.',
		onRetry
	}: { message?: string; onRetry?: () => void } = $props();
</script>

<div class="flex flex-col items-center justify-center gap-3 py-16 text-center">
	<svg class="h-10 w-10 text-rose-400" viewBox="0 0 24 24" fill="none">
		<circle cx="12" cy="12" r="9" stroke="currentColor" stroke-width="1.5" />
		<path d="M12 7.5v5.5M12 16.5h.01" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" />
	</svg>
	<p class="max-w-sm text-sm font-medium text-rose-600">{message}</p>
	{#if onRetry}
		<button
			type="button"
			onclick={onRetry}
			class="rounded-md border border-slate-300 bg-white px-3 py-1.5 text-sm font-medium text-slate-700 hover:bg-slate-50"
		>
			Повторить
		</button>
	{/if}
</div>
