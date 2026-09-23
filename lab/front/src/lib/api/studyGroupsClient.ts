import { request, encodePathSegment } from './httpClient';
import type {
	StudyGroup,
	StudyGroupCreate,
	StudyGroupUpdate,
	FilterCondition,
	SortCondition,
	PageResponse
} from '$lib/types/api';

/** `field[operator]=value` — LHS bracket notation used by the `filter` param. */
function serializeFilter(condition: FilterCondition): string {
	return `${condition.field}[${condition.operator.toLowerCase()}]=${condition.value}`;
}

/** `field[direction]` — notation used by the `sort` param. */
function serializeSort(condition: SortCondition): string {
	return `${condition.field}[${condition.direction}]`;
}

export interface GetStudyGroupsOptions {
	filters?: FilterCondition[];
	sorts?: SortCondition[];
	page?: number;
	pageSize?: number;
}

/** `GET /study-groups` — list, with filtering, sorting and pagination. */
export function getStudyGroups(options: GetStudyGroupsOptions = {}): Promise<PageResponse<StudyGroup>> {
	const { filters = [], sorts = [], page, pageSize } = options;
	return request<PageResponse<StudyGroup>>('/study-groups', {
		method: 'GET',
		params: {
			filter: filters.map(serializeFilter),
			sort: sorts.map(serializeSort),
			page,
			pageSize
		}
	});
}

/** `GET /study-groups/{id}` */
export function getStudyGroupById(id: number): Promise<StudyGroup> {
	return request<StudyGroup>(`/study-groups/${encodePathSegment(id)}`, { method: 'GET' });
}

/** `POST /study-groups` — `id` and `creation-date` are server-generated. */
export function createStudyGroup(payload: StudyGroupCreate): Promise<StudyGroup> {
	return request<StudyGroup>('/study-groups', { method: 'POST', body: payload });
}

/** `PUT /study-groups/{id}` — full update; `id` / `creation-date` untouched. */
export function updateStudyGroup(id: number, payload: StudyGroupUpdate): Promise<StudyGroup> {
	return request<StudyGroup>(`/study-groups/${encodePathSegment(id)}`, {
		method: 'PUT',
		body: payload
	});
}

/** `DELETE /study-groups/{id}` */
export function deleteStudyGroup(id: number): Promise<void> {
	return request<void>(`/study-groups/${encodePathSegment(id)}`, { method: 'DELETE' });
}
