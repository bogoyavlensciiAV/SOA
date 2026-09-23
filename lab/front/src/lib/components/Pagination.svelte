<script lang="ts">
	let {
		page,
		pageSize,
		total,
		totalPages,
		disabled = false,
		onPageChange,
		onPageSizeChange
	}: {
		page: number;
		pageSize: number;
		total: number;
		totalPages: number;
		disabled?: boolean;
		onPageChange: (page: number) => void;
		onPageSizeChange: (pageSize: number) => void;
	} = $props();

	const hasNextPage = $derived(page < totalPages - 1);
	const pageSizeOptions = [10, 25, 50, 100];
</script>

<div class="flex flex-wrap items-center justify-between gap-3 border-t border-slate-200 px-4 py-3">
	<div class="flex items-center gap-2 text-sm text-slate-600">
		<span>Строк на странице</span>
		<select
			class="rounded-md border border-slate-300 bg-white px-2 py-1 text-sm focus:border-sky-500 focus:outline-none"
			value={pageSize}
			{disabled}
			onchange={(e) => onPageSizeChange(Number(e.currentTarget.value))}
		>
			{#each pageSizeOptions as size (size)}
				<option value={size}>{size}</option>
			{/each}
		</select>
	</div>

	<div class="flex items-center gap-2">
		<span class="text-sm text-slate-500">Страница {page + 1}</span>
		<button
			type="button"
			class="rounded-md border border-slate-300 bg-white px-3 py-1.5 text-sm font-medium text-slate-700 disabled:cursor-not-allowed disabled:opacity-40 hover:enabled:bg-slate-50"
			disabled={disabled || page <= 0}
			onclick={() => onPageChange(page - 1)}
		>
			Назад
		</button>
		<button
			type="button"
			class="rounded-md border border-slate-300 bg-white px-3 py-1.5 text-sm font-medium text-slate-700 disabled:cursor-not-allowed disabled:opacity-40 hover:enabled:bg-slate-50"
			disabled={disabled || !hasNextPage}
			onclick={() => onPageChange(page + 1)}
		>
			Вперёд
		</button>
	</div>
</div>
