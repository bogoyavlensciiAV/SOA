<script lang="ts">
	import { onMount } from 'svelte';
	import DataTable from '$lib/components/DataTable.svelte';
	import Pagination from '$lib/components/Pagination.svelte';
	import FilterPanel from '$lib/components/FilterPanel.svelte';
	import Modal from '$lib/components/Modal.svelte';
	import StudyGroupForm from '$lib/components/StudyGroupForm.svelte';
	import LoadingState from '$lib/components/LoadingState.svelte';
	import EmptyState from '$lib/components/EmptyState.svelte';
	import ErrorState from '$lib/components/ErrorState.svelte';
	import {
		getStudyGroups,
		createStudyGroup,
		updateStudyGroup,
		deleteStudyGroup
	} from '$lib/api/studyGroupsClient';
	import { ApiError } from '$lib/api/httpClient';
	import { toastError, toastSuccess } from '$lib/toast';
	import { DEFAULT_PAGE_SIZE } from '$lib/config';
	import type {
		StudyGroup,
		StudyGroupCreate,
		FilterCondition,
		SortCondition,

        LhsDefinition

	} from '$lib/types/api';
    import { getLhsDefinitions } from '$lib/api/lhsDefinitionClient';

	let groups = $state<StudyGroup[]>([]);
	let total = $state(0);
	let totalPages = $state(0);
	let loading = $state(true);
	let loadError = $state<string | null>(null);

	let page = $state(0);
	let pageSize = $state(DEFAULT_PAGE_SIZE);
	let filters = $state<FilterCondition[]>([]);
	let sorts = $state<SortCondition[]>([]);

	// create / edit modal
	let formOpen = $state(false);
	let formSubmitting = $state(false);
	let editingGroup = $state<StudyGroup | null>(null);

	// view (details) modal
	let viewingGroup = $state<StudyGroup | null>(null);

	// delete confirmation
	let deletingGroup = $state<StudyGroup | null>(null);
	let deleteSubmitting = $state(false);

	let lhsDefinitions = $state<LhsDefinition[]>([]);

	async function load() {
		loading = true;
		loadError = null;
		try {
			const response = await getStudyGroups({ filters, sorts, page, pageSize });
			lhsDefinitions = await getLhsDefinitions();
			groups = response.content;
			total = response.total;
			totalPages = response.totalPages;
			console.log(groups);
		} catch (err) {
			loadError = err instanceof ApiError ? err.message : 'Не удалось загрузить учебные группы.';
		} finally {
			loading = false;
		}
	}

	onMount(load);

	function goToPage(next: number) {
		page = Math.max(0, next);
		load();
	}

	function changePageSize(size: number) {
		pageSize = size;
		page = 0;
		load();
	}

	function applyFilters(nextFilters: FilterCondition[], nextSorts: SortCondition[]) {
		filters = nextFilters;
		sorts = nextSorts;
		page = 0;
		load();
	}

	function clearFilters() {
		filters = [];
		sorts = [];
		page = 0;
		load();
	}

	function openCreate() {
		editingGroup = null;
		formOpen = true;
	}

	function openEdit(group: StudyGroup) {
		editingGroup = group;
		formOpen = true;
	}

	function closeForm() {
		if (formSubmitting) return;
		formOpen = false;
		editingGroup = null;
	}

	async function submitForm(payload: StudyGroupCreate) {
		formSubmitting = true;
		try {
			if (editingGroup) {
				await updateStudyGroup(editingGroup.id, payload);
				toastSuccess(`Группа «${payload.name}» обновлена.`);
			} else {
				await createStudyGroup(payload);
				toastSuccess(`Группа «${payload.name}» создана.`);
			}
			formOpen = false;
			editingGroup = null;
			await load();
		} catch (err) {
			toastError(err, 'Не удалось сохранить учебную группу.');
		} finally {
			formSubmitting = false;
		}
	}

	function requestDelete(group: StudyGroup) {
		deletingGroup = group;
	}

	async function confirmDelete() {
		if (!deletingGroup) return;
		deleteSubmitting = true;
		try {
			await deleteStudyGroup(deletingGroup.id);
			toastSuccess(`Группа «${deletingGroup.name}» удалена.`);
			deletingGroup = null;
			if (page > 0 && page >= totalPages - 1) {
				page -= 1;
			}
			await load();
		} catch (err) {
			toastError(err, 'Не удалось удалить учебную группу.');
		} finally {
			deleteSubmitting = false;
		}
	}
</script>

<div class="space-y-4">
	<div class="flex flex-wrap items-center justify-between gap-3">
		<div>
			<h1 class="text-lg font-semibold text-slate-900">Study Groups</h1>
			<p class="text-sm text-slate-500">Коллекция учебных групп: просмотр, фильтрация, CRUD.</p>
		</div>
		<button
			type="button"
			class="rounded-md bg-sky-600 px-4 py-2 text-sm font-medium text-white hover:bg-sky-700"
			onclick={openCreate}
		>
			+ Новая группа
		</button>
	</div>

	<FilterPanel {filters} {sorts} disabled={loading} definitions={lhsDefinitions} onApply={applyFilters} onClear={clearFilters} />

	<div class="rounded-lg border border-slate-200 bg-white shadow-sm">
		{#if loading}
			<LoadingState label="Загрузка учебных групп…" />
		{:else if loadError}
			<ErrorState message={loadError} onRetry={load} />
		{:else if groups.length === 0}
			<EmptyState
				title="Учебные группы не найдены"
				description="Измените условия фильтрации или создайте новую группу."
			/>
		{:else}
			<DataTable {groups} onView={(g) => (viewingGroup = g)} onEdit={openEdit} onDelete={requestDelete} />
			<Pagination
				{page}
				{pageSize}
				{total}
				{totalPages}
				disabled={loading}
				onPageChange={goToPage}
				onPageSizeChange={changePageSize}
			/>
		{/if}
	</div>
</div>

<!-- Create / edit -->
<Modal
	open={formOpen}
	title={editingGroup ? `Редактировать группу #${editingGroup.id}` : 'Новая учебная группа'}
	onClose={closeForm}
	widthClass="max-w-2xl"
>
	{#snippet children()}
		<StudyGroupForm
			initial={editingGroup ?? undefined}
			submitting={formSubmitting}
			onSubmit={submitForm}
			onCancel={closeForm}
		/>
	{/snippet}
</Modal>

<!-- View details -->
<Modal
	open={viewingGroup !== null}
	title={viewingGroup ? `Группа #${viewingGroup.id}: ${viewingGroup.name}` : 'Группа'}
	onClose={() => (viewingGroup = null)}
>
	{#snippet children()}
		{#if viewingGroup}
			<dl class="grid grid-cols-2 gap-x-4 gap-y-3 text-sm">
				<dt class="text-slate-500">ID</dt>
				<dd class="text-slate-900">{viewingGroup.id}</dd>

				<dt class="text-slate-500">Название</dt>
				<dd class="text-slate-900">{viewingGroup.name}</dd>

				<dt class="text-slate-500">Coordinates</dt>
				<dd class="text-slate-900">x: {viewingGroup.coordinates.x ?? '—'}, y: {viewingGroup.coordinates.y}</dd>

				<dt class="text-slate-500">Дата создания</dt>
				<dd class="text-slate-900">{viewingGroup['creation-date']}</dd>

				<dt class="text-slate-500">Students count</dt>
				<dd class="text-slate-900">{viewingGroup['students-count']}</dd>

				<dt class="text-slate-500">Expelled students</dt>
				<dd class="text-slate-900">{viewingGroup['expelled-students']}</dd>

				<dt class="text-slate-500">Form of education</dt>
				<dd class="text-slate-900">{viewingGroup['form-of-education']}</dd>

				<dt class="text-slate-500">Semester</dt>
				<dd class="text-slate-900">{viewingGroup['semester-enum'] ?? '—'}</dd>

				<dt class="col-span-2 mt-2 border-t border-slate-100 pt-2 font-medium text-slate-700">Group admin</dt>
				{#if viewingGroup['group-admin']}
					{@const admin = viewingGroup['group-admin']}
					<dt class="text-slate-500">Имя</dt>
					<dd class="text-slate-900">{admin.name}</dd>
					<dt class="text-slate-500">Вес</dt>
					<dd class="text-slate-900">{admin.weight}</dd>
					<dt class="text-slate-500">PassportID</dt>
					<dd class="text-slate-900">{admin.passportID ?? '—'}</dd>
					<dt class="text-slate-500">Location</dt>
					<dd class="text-slate-900">
						{#if admin.location}
							x: {admin.location.x ?? '—'}, y: {admin.location.y ?? '—'}, name: {admin.location.name ?? '—'}
						{:else}
							—
						{/if}
					</dd>
				{:else}
					<dd class="col-span-2 text-slate-500">— не задан —</dd>
				{/if}
			</dl>
		{/if}
	{/snippet}
</Modal>

<!-- Delete confirmation -->
<Modal
	open={deletingGroup !== null}
	title="Удалить учебную группу?"
	onClose={() => {
		if (!deleteSubmitting) deletingGroup = null;
	}}
	widthClass="max-w-md"
>
	{#snippet children()}
		{#if deletingGroup}
			<p class="text-sm text-slate-600">
				Вы уверены, что хотите удалить группу «{deletingGroup.name}» (ID {deletingGroup.id})? Это действие
				необратимо.
			</p>
		{/if}
	{/snippet}
	{#snippet footer()}
		<button
			type="button"
			class="rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-50"
			disabled={deleteSubmitting}
			onclick={() => (deletingGroup = null)}
		>
			Отмена
		</button>
		<button
			type="button"
			class="rounded-md bg-rose-600 px-4 py-2 text-sm font-medium text-white hover:bg-rose-700 disabled:opacity-50"
			disabled={deleteSubmitting}
			onclick={confirmDelete}
		>
			{deleteSubmitting ? 'Удаление…' : 'Удалить'}
		</button>
	{/snippet}
</Modal>
