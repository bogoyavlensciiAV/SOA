<script lang="ts">
	import { untrack } from 'svelte';
	import {
		FORM_OF_EDUCATION_VALUES,
		SEMESTER_VALUES,
		type StudyGroupCreate,
		type FormOfEducation,
		type Semester
	} from '$lib/types/api';

	interface FormState {
		name: string;
		coordinatesX: string;
		coordinatesY: string;
		studentsCount: string;
		expelledStudents: string;
		formOfEducation: FormOfEducation;
		semesterEnum: Semester | '';
		hasAdmin: boolean;
		adminName: string;
		adminWeight: string;
		adminPassportId: string;
		hasAdminLocation: boolean;
		adminLocationX: string;
		adminLocationY: string;
		adminLocationName: string;
	}

	let {
		initial,
		submitting = false,
		onSubmit,
		onCancel
	}: {
		initial?: Partial<StudyGroupCreate>;
		submitting?: boolean;
		onSubmit: (payload: StudyGroupCreate) => void;
		onCancel: () => void;
	} = $props();

	function toStr(v: number | null | undefined): string {
		return v === null || v === undefined ? '' : String(v);
	}

	// `initial` is only used to seed the form once when it's opened (the form
	// owns its own editable state afterwards), so these reads are deliberately
	// untracked.
	const { admin, location } = untrack(() => {
		const a = initial?.['group-admin'];
		return { admin: a, location: a?.location };
	});

	let form = $state<FormState>(
		untrack(() => ({
			name: initial?.name ?? '',
			coordinatesX: toStr(initial?.coordinates?.x),
			coordinatesY: toStr(initial?.coordinates?.y),
			studentsCount: toStr(initial?.['students-count']),
			expelledStudents: toStr(initial?.['expelled-students']),
			formOfEducation: initial?.['form-of-education'] ?? 'FULL_TIME_EDUCATION',
			semesterEnum: initial?.['semester-enum'] ?? '',
			hasAdmin: !!admin,
			adminName: admin?.name ?? '',
			adminWeight: toStr(admin?.weight),
			adminPassportId: admin?.passportID ?? '',
			hasAdminLocation: !!location,
			adminLocationX: toStr(location?.x),
			adminLocationY: toStr(location?.y),
			adminLocationName: location?.name ?? ''
		}))
	);

	let errors = $state<Record<string, string>>({});

	function validate(): boolean {
		const e: Record<string, string> = {};

		if (!form.name.trim()) e.name = 'Название обязательно.';

		if (Number.isNaN(Number(form.coordinatesY))) {
			e.coordinatesY = 'Координата Y обязательна и должна быть числом.';
		} else if (Number(form.coordinatesY) > 189) {
			e.coordinatesY = 'Координата Y не может быть больше 189.';
		}

		if (Number.isNaN(Number(form.coordinatesX))) e.coordinatesX = 'Координата X должна быть числом.';
		else if (Number(form.coordinatesX) > 726) e.coordinatesX = 'Координата X не может быть больше 726.';


		if (!Number.isInteger(Number(form.studentsCount)) || Number(form.studentsCount) < 1) {
			e.studentsCount = 'Количество студентов должно быть целым числом ≥ 1.';
		}
		if (!Number.isInteger(Number(form.expelledStudents)) || Number(form.expelledStudents) < 1) {
			e.expelledStudents = 'Количество отчисленных должно быть целым числом ≥ 1.';
		}

		if (form.hasAdmin) {
			if (!form.adminName.trim()) e.adminName = 'Имя администратора обязательно.';
			const weight = Number(form.adminWeight);
			if (Number.isNaN(weight) || weight <= 0) {
				e.adminWeight = 'Вес должен быть числом больше 0.';
			}
		}

		errors = e;
		return Object.keys(e).length === 0;
	}

	function submit(event: SubmitEvent) {
		event.preventDefault();
		if (!validate()) return;

		const payload: StudyGroupCreate = {
			name: form.name.trim(),
			coordinates: {
				y: Number(form.coordinatesY),
				x: Number(form.coordinatesX)
			},
			'students-count': Number(form.studentsCount),
			'expelled-students': Number(form.expelledStudents),
			'form-of-education': form.formOfEducation,
			'semester-enum': form.semesterEnum === '' ? null : form.semesterEnum,
			'group-admin': form.hasAdmin
				? {
						name: form.adminName.trim(),
						weight: Number(form.adminWeight),
						passportID: form.adminPassportId.trim() === '' ? null : form.adminPassportId.trim(),
						location: form.hasAdminLocation
							? {
									x: form.adminLocationX.trim() === '' ? null : Number(form.adminLocationX),
									y: form.adminLocationY.trim() === '' ? null : Number(form.adminLocationY),
									name: form.adminLocationName.trim() === '' ? null : form.adminLocationName.trim()
								}
							: null
					}
				: null,

		};

		onSubmit(payload);
	}
</script>

<form onsubmit={submit} class="space-y-6">
	<fieldset class="space-y-3">
		<legend class="text-sm font-semibold text-slate-900">Основные поля</legend>

		<div>
			<label class="mb-1 block text-sm font-medium text-slate-700" for="name">Название *</label>
			<input
				id="name"
				type="text"
				class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.name ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
				bind:value={form.name}
			/>
			{#if errors.name}<p class="mt-1 text-xs text-rose-600">{errors.name}</p>{/if}
		</div>

		<div class="grid grid-cols-2 gap-3">
			<div>
				<label class="mb-1 block text-sm font-medium text-slate-700" for="cx">Coordinates.x (опц., ≤ 726)</label>
				<input
					id="cx"
					type="number"
					class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.coordinatesX ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
					bind:value={form.coordinatesX}
				/>
				{#if errors.coordinatesX}<p class="mt-1 text-xs text-rose-600">{errors.coordinatesX}</p>{/if}
			</div>
			<div>
				<label class="mb-1 block text-sm font-medium text-slate-700" for="cy">Coordinates.y * (≤ 189)</label>
				<input
					id="cy"
					type="number"
					class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.coordinatesY ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
					bind:value={form.coordinatesY}
				/>
				{#if errors.coordinatesY}<p class="mt-1 text-xs text-rose-600">{errors.coordinatesY}</p>{/if}
			</div>
		</div>

		<div class="grid grid-cols-2 gap-3">
			<div>
				<label class="mb-1 block text-sm font-medium text-slate-700" for="sc">Students count * (≥ 1)</label>
				<input
					id="sc"
					type="number"
					class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.studentsCount ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
					bind:value={form.studentsCount}
				/>
				{#if errors.studentsCount}<p class="mt-1 text-xs text-rose-600">{errors.studentsCount}</p>{/if}
			</div>
			<div>
				<label class="mb-1 block text-sm font-medium text-slate-700" for="es">Expelled students * (≥ 1)</label>
				<input
					id="es"
					type="number"
					class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.expelledStudents ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
					bind:value={form.expelledStudents}
				/>
				{#if errors.expelledStudents}<p class="mt-1 text-xs text-rose-600">{errors.expelledStudents}</p>{/if}
			</div>
		</div>

		<div class="grid grid-cols-2 gap-3">
			<div>
				<label class="mb-1 block text-sm font-medium text-slate-700" for="foe">Form of education *</label>
				<select
					id="foe"
					class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
					bind:value={form.formOfEducation}
				>
					{#each FORM_OF_EDUCATION_VALUES as v (v)}
						<option value={v}>{v}</option>
					{/each}
				</select>
			</div>
			<div>
				<label class="mb-1 block text-sm font-medium text-slate-700" for="sem">Semester (nullable)</label>
				<select
					id="sem"
					class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
					bind:value={form.semesterEnum}
				>
					<option value="">— не задано —</option>
					{#each SEMESTER_VALUES as v (v)}
						<option value={v}>{v}</option>
					{/each}
				</select>
			</div>
		</div>
	</fieldset>

	<fieldset class="space-y-3 border-t border-slate-200 pt-4">
		<label class="flex items-center gap-2 text-sm font-semibold text-slate-900">
			<input type="checkbox" bind:checked={form.hasAdmin} class="rounded border-slate-300" />
			Администратор группы (group-admin)
		</label>

		{#if form.hasAdmin}
			<div class="space-y-3 rounded-md bg-slate-50 p-3">
				<div class="grid grid-cols-2 gap-3">
					<div>
						<label class="mb-1 block text-sm font-medium text-slate-700" for="an">Имя *</label>
						<input
							id="an"
							type="text"
							class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.adminName ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
							bind:value={form.adminName}
						/>
						{#if errors.adminName}<p class="mt-1 text-xs text-rose-600">{errors.adminName}</p>{/if}
					</div>
					<div>
						<label class="mb-1 block text-sm font-medium text-slate-700" for="aw">Вес * (&gt; 0)</label>
						<input
							id="aw"
							type="number"
							step="0.1"
							class="w-full rounded-md border px-3 py-1.5 text-sm focus:outline-none {errors.adminWeight ? 'border-rose-400' : 'border-slate-300 focus:border-sky-500'}"
							bind:value={form.adminWeight}
						/>
						{#if errors.adminWeight}<p class="mt-1 text-xs text-rose-600">{errors.adminWeight}</p>{/if}
					</div>
				</div>

				<div>
					<label class="mb-1 block text-sm font-medium text-slate-700" for="ap">PassportID (nullable)</label>
					<input
						id="ap"
						type="text"
						placeholder="— не задано —"
						class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
						bind:value={form.adminPassportId}
					/>
				</div>

				<label class="flex items-center gap-2 text-sm font-medium text-slate-700">
					<input type="checkbox" bind:checked={form.hasAdminLocation} class="rounded border-slate-300" />
					Местоположение (location)
				</label>

				{#if form.hasAdminLocation}
					<div class="grid grid-cols-3 gap-3">
						<div>
							<label class="mb-1 block text-sm font-medium text-slate-700" for="alx">X</label>
							<input
								id="alx"
								type="number"
								class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
								bind:value={form.adminLocationX}
							/>
						</div>
						<div>
							<label class="mb-1 block text-sm font-medium text-slate-700" for="aly">Y</label>
							<input
								id="aly"
								type="number"
								class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
								bind:value={form.adminLocationY}
							/>
						</div>
						<div>
							<label class="mb-1 block text-sm font-medium text-slate-700" for="aln">Название</label>
							<input
								id="aln"
								type="text"
								class="w-full rounded-md border border-slate-300 px-3 py-1.5 text-sm focus:border-sky-500 focus:outline-none"
								bind:value={form.adminLocationName}
							/>
						</div>
					</div>
				{/if}
			</div>
		{/if}
	</fieldset>

	<div class="flex justify-end gap-2 border-t border-slate-200 pt-4">
		<button
			type="button"
			class="rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-50"
			onclick={onCancel}
			disabled={submitting}
		>
			Отмена
		</button>
		<button
			type="submit"
			class="rounded-md bg-sky-600 px-4 py-2 text-sm font-medium text-white hover:bg-sky-700 disabled:opacity-50"
			disabled={submitting}
		>
			{submitting ? 'Сохранение…' : 'Сохранить'}
		</button>
	</div>
</form>
