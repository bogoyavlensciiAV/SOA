import { request, encodePathSegment } from './httpClient';
import type {
	StudyGroup,
	StudyGroupCreate,
	StudyGroupUpdate,
	FilterCondition,
	SortCondition,
	PageResponse
} from '$lib/types/api';

function serializeFilter(condition: FilterCondition): string {
	return `${condition.field}[${condition.operator.toLowerCase()}]=${condition.value}`;
}

function serializeSort(condition: SortCondition): string {
	return `${condition.field}[${condition.direction}]`;
}

export interface GetStudyGroupsOptions {
	filters?: FilterCondition[];
	sorts?: SortCondition[];
	page?: number;
	pageSize?: number;
}

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

export function getStudyGroupById(id: number): Promise<StudyGroup> {
	return request<StudyGroup>(`/study-groups/${encodePathSegment(id)}`, { method: 'GET' });
}

export function createStudyGroup(payload: StudyGroupCreate): Promise<StudyGroup> {
	return request<StudyGroup>('/study-groups', { method: 'POST', body: payload });
}

export function updateStudyGroup(id: number, payload: StudyGroupUpdate): Promise<StudyGroup> {
	return request<StudyGroup>(`/study-groups/${encodePathSegment(id)}`, {
		method: 'PUT',
		body: payload
	});
}

export function deleteStudyGroup(id: number): Promise<void> {
	return request<void>(`/study-groups/${encodePathSegment(id)}`, { method: 'DELETE' });
}
