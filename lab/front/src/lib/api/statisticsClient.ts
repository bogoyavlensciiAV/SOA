import { request, encodePathSegment } from './httpClient';
import type { ExpelledStudentsSum, CountResult, StudyGroup } from '$lib/types/api';

export function sumExpelledStudents(): Promise<ExpelledStudentsSum> {
	return request<ExpelledStudentsSum>('/study-groups/expelled-students/sum', { method: 'GET' }, false);
}

export function countByStudentsCount(studentsCount: number): Promise<CountResult> {
	return request<CountResult>(
		`/study-groups/students-count/${encodePathSegment(studentsCount)}/count`,
		{ method: 'GET' },
		false
	);
}

export function findByNamePrefix(prefix: string): Promise<StudyGroup[]> {
	return request<StudyGroup[]>(`/study-groups/name/starts-with/${encodePathSegment(prefix)}`, {
		method: 'GET'
	}, false);
}
