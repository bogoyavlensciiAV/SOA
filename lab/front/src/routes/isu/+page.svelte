<script lang="ts">
	import IsuActionCard from '$lib/components/IsuActionCard.svelte';
	import { moveStudents, changeEducationForm } from '$lib/api/isuClient';
	import { ApiError } from '$lib/api/httpClient';
	import { toastError, toastSuccess } from '$lib/toast';
	import { FORM_OF_EDUCATION_VALUES, type FormOfEducation } from '$lib/types/api';

	// Move students between groups
	let fromGroup = $state('');
	let toGroup = $state('');
	let moveSubmitting = $state(false);

	function validPositiveInt(value: string): number | null {
		const n = Number(value);
		return Number.isInteger(n) && n >= 1 ? n : null;
	}

	async function submitMove(event: SubmitEvent) {
		event.preventDefault();
		const from = validPositiveInt(fromGroup);
		const to = validPositiveInt(toGroup);
		if (from === null || to === null) {
			toastError(new Error('ID групп должны быть целыми числами ≥ 1.'));
			return;
		}
		if (from === to) {
			toastError(new Error('Исходная и целевая группы должны различаться.'));
			return;
		}
		moveSubmitting = true;
		try {
			await moveStudents(from, to);
			toastSuccess(`Студенты перемещены из группы ${from} в группу ${to}.`);
			fromGroup = '';
			toGroup = '';
		} catch (err) {
			const message =
				err instanceof ApiError && err.status === 409
					? err.message || 'Невозможно переместить студентов из группы в саму себя.'
					: undefined;
			toastError(err, message);
		} finally {
			moveSubmitting = false;
		}
	}

	// Change education form
	let eduGroupId = $state('');
	let newForm = $state<FormOfEducation>('FULL_TIME_EDUCATION');
	let eduSubmitting = $state(false);

	async function submitEduForm(event: SubmitEvent) {
		event.preventDefault();
		const id = validPositiveInt(eduGroupId);
		if (id === null) {
			toastError(new Error('ID группы должен быть целым числом ≥ 1.'));
			return;
		}
		eduSubmitting = true;
		try {
			const updated = await changeEducationForm(id, newForm);
			toastSuccess(`Форма обучения группы «${updated.name}» изменена на ${newForm}.`);
			eduGroupId = '';
		} catch (err) {
			toastError(err, 'Не удалось изменить форму обучения.');
		} finally {
			eduSubmitting = false;
		}
	}
</script>

<div class="space-y-4">
	<div>
		<h1 class="text-lg font-semibold text-slate-900">ISU</h1>
		<p class="text-sm text-slate-500">Дополнительные операции второго веб-сервиса /isu.</p>
	</div>

	<div class="grid gap-4">
		<IsuActionCard
			title="Перемещение студентов между группами"
			description="POST /isu/group/{'{from-group}'}/move/{'{to-group}'}"
		>
			<form onsubmit={submitMove} class="flex flex-wrap items-end gap-3">
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="from-group">ID исходной группы</label>
					<input
						id="from-group"
						type="number"
						min="1"
						required
						class="w-40 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={fromGroup}
					/>
				</div>
				<div class="pb-2 text-slate-400">→</div>
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="to-group">ID целевой группы</label>
					<input
						id="to-group"
						type="number"
						min="1"
						required
						class="w-40 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={toGroup}
					/>
				</div>
				<button
					type="submit"
					class="rounded-md bg-sky-600 px-4 py-2 text-sm font-medium text-white hover:bg-sky-700 disabled:opacity-50"
					disabled={moveSubmitting}
				>
					{moveSubmitting ? 'Выполняется…' : 'Переместить'}
				</button>
			</form>
		</IsuActionCard>

		<IsuActionCard
			title="Изменение формы обучения"
			description="POST /isu/group/{'{group-id}'}/change-edu-form/{'{new-form}'}"
		>
			<form onsubmit={submitEduForm} class="flex flex-wrap items-end gap-3">
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="edu-group-id">ID группы</label>
					<input
						id="edu-group-id"
						type="number"
						min="1"
						required
						class="w-40 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={eduGroupId}
					/>
				</div>
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="new-form">Новая форма обучения</label>
					<select
						id="new-form"
						class="w-56 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={newForm}
					>
						{#each FORM_OF_EDUCATION_VALUES as v (v)}
							<option value={v}>{v}</option>
						{/each}
					</select>
				</div>
				<button
					type="submit"
					class="rounded-md bg-sky-600 px-4 py-2 text-sm font-medium text-white hover:bg-sky-700 disabled:opacity-50"
					disabled={eduSubmitting}
				>
					{eduSubmitting ? 'Выполняется…' : 'Изменить'}
				</button>
			</form>
		</IsuActionCard>
	</div>
</div>
