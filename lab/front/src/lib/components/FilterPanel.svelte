<script lang="ts">
	import { untrack } from 'svelte';
	import {
		type FilterCondition,
		type LhsDefinition,
		type SortCondition
	} from '$lib/types/api';

	let {
		filters,
		sorts,
		disabled = false,
		definitions,
		onApply,
		onClear
	}: {
		filters: FilterCondition[];
		sorts: SortCondition[];
		disabled?: boolean;
		definitions: LhsDefinition[];
		onApply: (filters: FilterCondition[], sorts: SortCondition[]) => void;
		onClear: () => void;
	} = $props();

	// Local, editable copies — only pushed up when "Применить" is clicked.
	// Deliberately captures only the initial value of `filters`/`sorts` (the
	// panel owns its own draft state until the user applies it).
	let draftFilters = $state<FilterCondition[]>(
		untrack(() => structuredClone($state.snapshot(filters)))
	);
	let draftSorts = $state<SortCondition[]>(
		untrack(() => structuredClone($state.snapshot(sorts)))
	);


	function addFilter() {
		draftFilters = [...draftFilters, { field: 'name', operator: 'EQ', value: '' }];
	}

	function removeFilter(index: number) {
		draftFilters = draftFilters.filter((_, i) => i !== index);
	}

	function addSort() {
		draftSorts = [...draftSorts, { field: 'name', direction: 'asc' }];
	}

	function removeSort(index: number) {
		draftSorts = draftSorts.filter((_, i) => i !== index);
	}

	function apply() {
		onApply(
			draftFilters.filter((f) => f.value.trim().length > 0),
			draftSorts
		);
	}

	function clear() {
		draftFilters = [];
		draftSorts = [];
		onClear();
	}
</script>

<div class="rounded-lg border border-slate-200 bg-white p-4 shadow-sm">
	<div class="flex flex-col gap-5 lg:flex-row lg:gap-8">
		<div class="flex-1">
			<div class="flex items-center justify-between">
				<h3 class="text-sm font-semibold text-slate-900">Фильтры</h3>
				<button type="button" class="text-xs font-medium text-sky-600 hover:text-sky-700" onclick={addFilter}>
					+ Условие
				</button>
			</div>

			{#if draftFilters.length === 0}
				<p class="mt-2 text-xs text-slate-400">Условия фильтрации не заданы.</p>
			{/if}

			<div class="mt-2 space-y-2">
				{#each draftFilters as filter, i (i)}
					<div class="flex flex-wrap items-center gap-2">
						<select
							class="min-w-[10rem] rounded-md border border-slate-300 px-2 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
							bind:value={filter.field}
						>
							{#each definitions.map(d => d.field) as field (field)}
								<option value={field}>{field}</option>
							{/each}
						</select>
						<select
							class="rounded-md border border-slate-300 px-2 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
							bind:value={filter.operator}
						>
							{#each definitions.find(d => d.field === filter.field)?.allowedOperators ?? [] as op (op)}
								<option value={op}>{op}</option>
							{/each}
						</select>
						<input
							type="text"
							placeholder="значение"
							class="w-32 flex-1 rounded-md border border-slate-300 px-2 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
							bind:value={filter.value}
						/>
						<button
							type="button"
							aria-label="Удалить условие"
							class="rounded-md p-1.5 text-slate-400 hover:bg-slate-100 hover:text-rose-500"
							onclick={() => removeFilter(i)}
						>
							<svg class="h-4 w-4" viewBox="0 0 20 20" fill="none">
								<path d="M5 5l10 10M15 5 5 15" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" />
							</svg>
						</button>
					</div>
				{/each}
			</div>
		</div>

		<div class="flex-1">
			<div class="flex items-center justify-between">
				<h3 class="text-sm font-semibold text-slate-900">Сортировка</h3>
				<button type="button" class="text-xs font-medium text-sky-600 hover:text-sky-700" onclick={addSort}>
					+ Поле
				</button>
			</div>

			{#if draftSorts.length === 0}
				<p class="mt-2 text-xs text-slate-400">Сортировка не задана.</p>
			{/if}

			<div class="mt-2 space-y-2">
				{#each draftSorts as sort, i (i)}
					<div class="flex flex-wrap items-center gap-2">
						<span class="w-5 text-xs text-slate-400">{i + 1}.</span>
						<select
							class="min-w-[10rem] rounded-md border border-slate-300 px-2 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
							bind:value={sort.field}
						>
							{#each definitions.map(d => d.field) as field (field)}
								<option value={field}>{field}</option>
							{/each}
						</select>
						<select
							class="rounded-md border border-slate-300 px-2 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
							bind:value={sort.direction}
						>
							<option value="asc">asc</option>
							<option value="desc">desc</option>
						</select>
						<button
							type="button"
							aria-label="Удалить сортировку"
							class="rounded-md p-1.5 text-slate-400 hover:bg-slate-100 hover:text-rose-500"
							onclick={() => removeSort(i)}
						>
							<svg class="h-4 w-4" viewBox="0 0 20 20" fill="none">
								<path d="M5 5l10 10M15 5 5 15" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" />
							</svg>
						</button>
					</div>
				{/each}
			</div>
		</div>
	</div>

	<div class="mt-4 flex justify-end gap-2 border-t border-slate-100 pt-3">
		<button
			type="button"
			class="rounded-md border border-slate-300 bg-white px-3 py-1.5 text-sm font-medium text-slate-700 hover:bg-slate-50"
			{disabled}
			onclick={clear}
		>
			Сбросить
		</button>
		<button
			type="button"
			class="rounded-md bg-sky-600 px-3 py-1.5 text-sm font-medium text-white hover:bg-sky-700 disabled:opacity-50"
			{disabled}
			onclick={apply}
		>
			Применить
		</button>
	</div>
</div>
