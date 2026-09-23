/**
 * TypeScript types mirroring the schemas defined in `OpenAPI.yaml`.
 * Field names keep the API's original kebab-case so that JSON payloads can be
 * used as-is without extra mapping.
 */

export type FormOfEducation = 'DISTANCE_EDUCATION' | 'FULL_TIME_EDUCATION' | 'EVENING_CLASSES';

export const FORM_OF_EDUCATION_VALUES: FormOfEducation[] = [
	'DISTANCE_EDUCATION',
	'FULL_TIME_EDUCATION',
	'EVENING_CLASSES'
];

export type Semester = 'FIRST' | 'THIRD' | 'SEVENTH';

export const SEMESTER_VALUES: Semester[] = ['FIRST', 'THIRD', 'SEVENTH'];

export interface Coordinates {
	/** Optional. Integer, maximum 726. */
	x?: number | null;
	/** Required. Integer, maximum 189. */
	y: number;
}

export interface Location {
	x?: number | null;
	y?: number | null;
	/** Nullable, minLength 1 when present. */
	name?: string | null;
}

export interface Person {
	/** Required, non-empty. */
	name: string;
	/** Required, must be > 0. */
	weight: number;
	/** Nullable. */
	passportID?: string | null;
	/** Nullable. */
	location?: Location | null;
}

export interface StudyGroup {
	/** Server-generated, read-only. */
	id: number;
	name: string;
	coordinates: Coordinates;
	'creation-date': string;
	'students-count': number;
	'expelled-students': number;
	'form-of-education': FormOfEducation;
	'semester-enum'?: Semester | null;
	'group-admin'?: Person | null;
}

/** Body for `POST /study-groups`. No `id` / `creation-date` — server-generated. */
export interface StudyGroupCreate {
	name: string;
	coordinates: Coordinates;
	'students-count': number;
	'expelled-students': number;
	'form-of-education': FormOfEducation;
	'semester-enum'?: Semester | null;
	'group-admin'?: Person | null;
}

/** Body for `PUT /study-groups/{id}`. No `id` / `creation-date` — server-generated. */
export interface StudyGroupUpdate {
	name: string;
	coordinates: Coordinates;
	'students-count': number;
	'expelled-students': number;
	'form-of-education': FormOfEducation;
	'semester-enum'?: Semester | null;
	'group-admin'?: Person | null;
}

export interface ExpelledStudentsSum {
	sum: number;
}

export interface CountResult {
	count: number;
}

export interface ApiErrorItem {
	code: string;
	message: string;
}

export interface ApiErrorBody {
	errors: ApiErrorItem[];
}

export type LhsOperator = 'EQ' | 'NE' | 'GT' | 'GTE' | 'LT' | 'LTE' | 'CONTAINS' | 'IN';

export interface LhsDefinition {
	field: string;
	allowedOperators: LhsOperator[];
	type: string;
}

export type SortDirection = 'asc' | 'desc';

export interface FilterCondition {
	field: string;
	operator: LhsOperator;
	value: string;
}

export interface SortCondition {
	field: string;
	direction: SortDirection;
}

export interface PageResponse<T> {
	content: T[];
	page: number;
	pageSize: number;
	total: number;
	totalPages: number;
}
