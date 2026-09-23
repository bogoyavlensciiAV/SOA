<script lang="ts">
	import type { StudyGroup } from '$lib/types/api';

	let {
		groups,
		onView,
		onEdit,
		onDelete
	}: {
		groups: StudyGroup[];
		onView: (group: StudyGroup) => void;
		onEdit: (group: StudyGroup) => void;
		onDelete: (group: StudyGroup) => void;
	} = $props();

	function formatAdmin(group: StudyGroup): string {
		const admin = group['group-admin'];
		if (!admin) return '—';
		return `${admin.name} (${admin.weight} кг)`;
	}

	function formatCoordinates(group: StudyGroup): string {
		const c = group.coordinates;
		return `x: ${c.x ?? '—'}, y: ${c.y}`;
	}
</script>

<div class="overflow-x-auto">
	<table class="w-full min-w-[900px] border-collapse text-sm">
		<thead>
			<tr class="border-b border-slate-200 bg-slate-50 text-left text-xs font-semibold uppercase tracking-wide text-slate-500">
				<th class="px-3 py-2.5">ID</th>
				<th class="px-3 py-2.5">Название</th>
				<th class="px-3 py-2.5">Coordinates</th>
				<th class="px-3 py-2.5">Создано</th>
				<th class="px-3 py-2.5">Студентов</th>
				<th class="px-3 py-2.5">Отчислено</th>
				<th class="px-3 py-2.5">Форма обучения</th>
				<th class="px-3 py-2.5">Семестр</th>
				<th class="px-3 py-2.5">Админ группы</th>
				<th class="px-3 py-2.5 text-right">Действия</th>
			</tr>
		</thead>
		<tbody>
			{#each groups as group (group.id)}
				<tr class="border-b border-slate-100 hover:bg-slate-50">
					<td class="px-3 py-2.5 text-slate-500">{group.id}</td>
					<td class="cell-truncate px-3 py-2.5 font-medium text-slate-900">{group.name}</td>
					<td class="px-3 py-2.5 text-slate-600">{formatCoordinates(group)}</td>
					<td class="px-3 py-2.5 text-slate-600">{group['creation-date']}</td>
					<td class="px-3 py-2.5 text-slate-600">{group['students-count']}</td>
					<td class="px-3 py-2.5 text-slate-600">{group['expelled-students']}</td>
					<td class="px-3 py-2.5 text-slate-600">
						<span class="rounded-full bg-slate-100 px-2 py-0.5 text-xs font-medium text-slate-700">
							{group['form-of-education']}
						</span>
					</td>
					<td class="px-3 py-2.5 text-slate-600">{group['semester-enum'] ?? '—'}</td>
					<td class="cell-truncate px-3 py-2.5 text-slate-600">{formatAdmin(group)}</td>
					<td class="px-3 py-2.5">
						<div class="flex justify-end gap-1">
							<button
								type="button"
								class="rounded-md p-1.5 text-slate-500 hover:bg-slate-100 hover:text-slate-700"
								title="Просмотреть"
								onclick={() => onView(group)}
							>
								<svg class="h-4 w-4" viewBox="0 0 20 20" fill="none">
									<path
										d="M1.5 10S4.5 4 10 4s8.5 6 8.5 6-3 6-8.5 6-8.5-6-8.5-6Z"
										stroke="currentColor"
										stroke-width="1.4"
									/>
									<circle cx="10" cy="10" r="2.3" stroke="currentColor" stroke-width="1.4" />
								</svg>
							</button>
							<button
								type="button"
								class="rounded-md p-1.5 text-slate-500 hover:bg-slate-100 hover:text-sky-600"
								title="Редактировать"
								onclick={() => onEdit(group)}
							>
								<svg class="h-4 w-4" viewBox="0 0 20 20" fill="none">
									<path
										d="M13.5 3.5 16.5 6.5 6.5 16.5H3.5v-3l10-10Z"
										stroke="currentColor"
										stroke-width="1.4"
										stroke-linejoin="round"
									/>
								</svg>
							</button>
							<button
								type="button"
								class="rounded-md p-1.5 text-slate-500 hover:bg-rose-50 hover:text-rose-600"
								title="Удалить"
								onclick={() => onDelete(group)}
							>
								<svg class="h-4 w-4" viewBox="0 0 20 20" fill="none">
									<path
										d="M4 6h12M8 6V4.5h4V6M5.5 6l.6 9.5a1 1 0 0 0 1 .9h5.8a1 1 0 0 0 1-.9L14.5 6"
										stroke="currentColor"
										stroke-width="1.4"
										stroke-linecap="round"
										stroke-linejoin="round"
									/>
								</svg>
							</button>
						</div>
					</td>
				</tr>
			{/each}
		</tbody>
	</table>
</div>
