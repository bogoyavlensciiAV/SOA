import type { FormOfEducation, Semester } from '$lib/types/api';

export const FORM_OF_EDUCATION_LABELS: Record<FormOfEducation, string> = {
	DISTANCE_EDUCATION: 'Заочная',
	FULL_TIME_EDUCATION: 'Очная',
	EVENING_CLASSES: 'Очно-заочная'
};

export const SEMESTER_LABELS: Record<Semester, string> = {
	FIRST: 'Первый',
	THIRD: 'Третий',
	SEVENTH: 'Седьмой'
};
