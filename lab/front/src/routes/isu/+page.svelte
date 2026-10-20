<script lang="ts">
	import { FORM_OF_EDUCATION_LABELS } from '$lib/labels';
	import IsuActionCard from '$lib/components/IsuActionCard.svelte';
	import { moveStudents, changeEducationForm } from '$lib/api/isuClient';
	import { ApiError } from '$lib/api/httpClient';
	import { toastError, toastSuccess } from '$lib/toast';
	import { FORM_OF_EDUCATION_VALUES, type FormOfEducation } from '$lib/types/api';

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
			toastSuccess(`Форма обучения группы «${updated.name}» изменена на ${FORM_OF_EDUCATION_LABELS[newForm]}.`);
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
	</div>

	<div class="grid gap-4">
		<IsuActionCard
			title="Перемещение студентов между группами"
		>
			<form onsubmit={submitMove} class="flex flex-wrap items-end gap-3">
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="from-group">ID исходной группы</label>
					<input
						id="from-group"
						type="number"
						min="1"
						required
						class="w-40 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-[#FF00FF] focus:outline-none"
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
						class="w-40 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-[#FF00FF] focus:outline-none"
						bind:value={toGroup}
					/>
				</div>
				<button
					type="submit"
					class="rounded-md bg-[#FF00FF] px-4 py-2 text-sm font-medium text-white hover:bg-[#FF00FF] disabled:opacity-50"
					disabled={moveSubmitting}
				>
					{moveSubmitting ? 'Выполняется…' : 'Переместить'}
				</button>
			</form>
		</IsuActionCard>

		<IsuActionCard
			title="Изменение формы обучения"
		>
			<form onsubmit={submitEduForm} class="flex flex-wrap items-end gap-3">
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="edu-group-id">ID группы</label>
					<input
						id="edu-group-id"
						type="number"
						min="1"
						required
						class="w-40 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-[#FF00FF] focus:outline-none"
						bind:value={eduGroupId}
					/>
				</div>
				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="new-form">Новая форма обучения</label>
					<select
						id="new-form"
						class="w-56 rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-[#FF00FF] focus:outline-none"
						bind:value={newForm}
					>
						{#each FORM_OF_EDUCATION_VALUES as v (v)}
							<option value={v}>{FORM_OF_EDUCATION_LABELS[v]}</option>
						{/each}
					</select>
				</div>
				<button
					type="submit"
					class="rounded-md bg-[#FF00FF] px-4 py-2 text-sm font-medium text-white hover:bg-[#FF00FF] disabled:opacity-50"
					disabled={eduSubmitting}
				>
					{eduSubmitting ? 'Выполняется…' : 'Изменить'}
				</button>
			</form>
		</IsuActionCard>
	</div>
</div>
