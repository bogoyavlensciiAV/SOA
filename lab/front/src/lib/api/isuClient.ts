import { request, encodePathSegment } from './httpClient';
import type { FormOfEducation, StudyGroup } from '$lib/types/api';

/** `POST /isu/group/{from-group}/move/{to-group}` */
export function moveStudents(fromGroup: number, toGroup: number): Promise<void> {
	return request<void>(
		`/isu/group/${encodePathSegment(fromGroup)}/move/${encodePathSegment(toGroup)}`,
		{ method: 'POST' }
	);
}

/** `POST /isu/group/{group-id}/change-edu-form/{new-form}` */
export function changeEducationForm(
	groupId: number,
	newForm: FormOfEducation
): Promise<StudyGroup> {
	return request<StudyGroup>(
		`/isu/group/${encodePathSegment(groupId)}/change-edu-form/${encodePathSegment(newForm)}`,
		{ method: 'POST' }
	);
}
