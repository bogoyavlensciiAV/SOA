<script lang="ts">
	import { onMount } from 'svelte';
	import StatisticsCard from '$lib/components/StatisticsCard.svelte';
	import BarChart from '$lib/components/BarChart.svelte';
	import LoadingState from '$lib/components/LoadingState.svelte';
	import ErrorState from '$lib/components/ErrorState.svelte';
	import { sumExpelledStudents, countByStudentsCount, findByNamePrefix } from '$lib/api/statisticsClient';
	import { ApiError } from '$lib/api/httpClient';
	import { toastError } from '$lib/toast';
	import type { StudyGroup } from '$lib/types/api';

	let sumLoading = $state(true);
	let sumError = $state<string | null>(null);
	let sumValue = $state<number | null>(null);

	async function loadSum() {
		sumLoading = true;
		sumError = null;
		try {
			const result = await sumExpelledStudents();
			sumValue = result.sum;
		} catch (err) {
			sumError = err instanceof ApiError ? err.message : 'Не удалось получить сумму.';
		} finally {
			sumLoading = false;
		}
	}

	onMount(loadSum);

	let studentsCountInput = $state('100');
	let countLoading = $state(false);
	let countError = $state<string | null>(null);
	let countValue = $state<number | null>(null);
	let countQueried = $state<number | null>(null);

	async function runCountByStudentsCount() {
		const value = Number(studentsCountInput);
		if (!Number.isInteger(value) || value < 1) {
			toastError(new Error('students-count должен быть целым числом ≥ 1.'));
			return;
		}
		countLoading = true;
		countError = null;
		try {
			const result = await countByStudentsCount(value);
			countValue = result.count;
			countQueried = value;
		} catch (err) {
			countError = err instanceof ApiError ? err.message : 'Не удалось получить количество.';
			toastError(err, countError);
		} finally {
			countLoading = false;
		}
	}

	let prefixInput = $state('');
	let prefixLoading = $state(false);
	let prefixError = $state<string | null>(null);
	let prefixResults = $state<StudyGroup[] | null>(null);
	let prefixQueried = $state<string | null>(null);

	async function runFindByPrefix() {
		const prefix = prefixInput.trim();
		if (prefix.length === 0) {
			toastError(new Error('Введите префикс названия.'));
			return;
		}
		prefixLoading = true;
		prefixError = null;
		try {
			prefixResults = await findByNamePrefix(prefix);
			prefixQueried = prefix;
		} catch (err) {
			prefixError = err instanceof ApiError ? err.message : 'Не удалось выполнить поиск.';
			toastError(err, prefixError);
		} finally {
			prefixLoading = false;
		}
	}
</script>

<div class="space-y-4">
	<div>
		<h1 class="text-lg font-semibold text-slate-900">Statistics</h1>
	</div>

	<div class="grid gap-4 lg:grid-cols-1">
		<StatisticsCard
			title="Сумма expelled-students"
		>
			{#if sumLoading}
				<LoadingState label="Загрузка…" />
			{:else if sumError}
				<ErrorState message={sumError} onRetry={loadSum} />
			{:else if sumValue !== null}
				<BarChart bars={[{ label: 'sum', value: sumValue }]} />
			{/if}
		</StatisticsCard>

		<StatisticsCard
			title="Количество групп по students-count"
		>
			<div class="mb-4 flex items-end gap-2">
				<div class="flex-1">
					<label class="mb-1 block text-sm font-medium text-slate-700" for="students-count-input">
						students-count (≥ 1)
					</label>
					<input
						id="students-count-input"
						type="number"
						min="1"
						class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={studentsCountInput}
					/>
				</div>
				<button
					type="button"
					class="rounded-md bg-sky-600 px-4 py-1.5 text-sm font-medium text-white hover:bg-sky-700 disabled:opacity-50"
					disabled={countLoading}
					onclick={runCountByStudentsCount}
				>
					{countLoading ? 'Запрос…' : 'Запросить'}
				</button>
			</div>

			{#if countError}
				<ErrorState message={countError} />
			{:else if countValue !== null && countQueried !== null}
				<BarChart bars={[{ label: String(countQueried), value: countValue }]} />
			{/if}
		</StatisticsCard>

		<StatisticsCard
			title="Поиск групп по началу названия"
		>
			<div class="mb-4 flex items-end gap-2">
				<div class="flex-1">
					<label class="mb-1 block text-sm font-medium text-slate-700" for="prefix-input">Префикс названия</label>
					<input
						id="prefix-input"
						type="text"
						placeholder="Math"
						class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={prefixInput}
					/>
				</div>
				<button
					type="button"
					class="rounded-md bg-sky-600 px-4 py-1.5 text-sm font-medium text-white hover:bg-sky-700 disabled:opacity-50"
					disabled={prefixLoading}
					onclick={runFindByPrefix}
				>
					{prefixLoading ? 'Запрос…' : 'Найти'}
				</button>
			</div>

			{#if prefixError}
				<ErrorState message={prefixError} />
			{:else if prefixResults !== null}
				<BarChart bars={[{ label: prefixQueried ?? '', value: prefixResults.length }]} />

				{#if prefixResults.length > 0}
					<div class="mt-4 max-h-56 overflow-y-auto rounded-md border border-slate-200">
						<table class="w-full text-left text-xs">
							<thead class="sticky top-0 bg-slate-50 text-slate-500">
								<tr>
									<th class="px-2 py-1.5">ID</th>
									<th class="px-2 py-1.5">Название</th>
									<th class="px-2 py-1.5">Форма обучения</th>
									<th class="px-2 py-1.5">Студентов</th>
								</tr>
							</thead>
							<tbody>
								{#each prefixResults as group (group.id)}
									<tr class="border-t border-slate-100">
										<td class="px-2 py-1.5 text-slate-500">{group.id}</td>
										<td class="px-2 py-1.5 font-medium text-slate-800">{group.name}</td>
										<td class="px-2 py-1.5 text-slate-600">{group['form-of-education']}</td>
										<td class="px-2 py-1.5 text-slate-600">{group['students-count']}</td>
									</tr>
								{/each}
							</tbody>
						</table>
					</div>
				{/if}
			{/if}
		</StatisticsCard>
	</div>
</div>
