import { API_BASE_URL, ISU_BASE_URL } from '$lib/config';
import type { ApiErrorBody } from '$lib/types/api';

export class ApiError extends Error {
	status: number;
	codes: string[];

	constructor(message: string, status: number, codes: string[] = []) {
		super(message);
		this.name = 'ApiError';
		this.status = status;
		this.codes = codes;
	}
}

export type QueryValue = string | number | boolean | null | undefined;
export type QueryParams = Record<string, QueryValue | QueryValue[]>;

function buildUrl(path: string,  isIsu: boolean, params?: QueryParams): string {
	const url = new URL(
		path.replace(/^\//, ''),
		isIsu ? ISU_BASE_URL.endsWith('/') ? ISU_BASE_URL : `${ISU_BASE_URL}/` : API_BASE_URL.endsWith('/') ? API_BASE_URL : `${API_BASE_URL}/`
	);

	if (params) {
		for (const [key, value] of Object.entries(params)) {
			if (value === undefined || value === null) continue;
			if (Array.isArray(value)) {
				for (const item of value) {
					if (item === undefined || item === null) continue;
					url.searchParams.append(key, String(item));
				}
			} else {
				url.searchParams.append(key, String(value));
			}
		}
	}

	return url.toString();
}

export function encodePathSegment(value: string | number): string {
	return encodeURIComponent(String(value));
}

async function parseErrorBody(response: Response): Promise<{ message: string; codes: string[] }> {
	try {
		const data = (await response.json()) as ApiErrorBody | unknown;
		if (
			data &&
			typeof data === 'object' &&
			Array.isArray((data as ApiErrorBody).errors) &&
			(data as ApiErrorBody).errors.length > 0
		) {
			const errors = (data as ApiErrorBody).errors;
			const message = errors.map((e) => e.message).join('; ');
			const codes = errors.map((e) => e.code);
			return { message, codes };
		}
	} catch {
	}
	return { message: defaultMessageForStatus(response.status), codes: [] };
}

function defaultMessageForStatus(status: number): string {
	switch (status) {
		case 400:
			return 'Некорректный запрос (400).';
		case 404:
			return 'Объект не найден (404).';
		case 409:
			return 'Конфликт при выполнении операции (409).';
		case 422:
			return 'Ошибка валидации данных (422).';
		case 500:
			return 'Внутренняя ошибка сервера (500).';
		default:
			return `Сервер вернул неожиданный статус (${status}).`;
	}
}

export interface RequestOptions {
	method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
	params?: QueryParams;
	body?: unknown;
}

export async function request<T>(path: string, options: RequestOptions = {}, isIsu: boolean): Promise<T> {
	const { method = 'GET', params, body } = options;
	const url = buildUrl(path, isIsu, params);

	let response: Response;
	try {
		response = await fetch(url, {
			method,
			headers: body !== undefined ? { 'Content-Type': 'application/json' } : undefined,
			body: body !== undefined ? JSON.stringify(body) : undefined
		});
	} catch {
		throw new ApiError(
			'Не удалось связаться с сервером. Проверьте подключение к сети и адрес API.',
			0
		);
	}

	if (!response.ok) {
		const { message, codes } = await parseErrorBody(response);
		throw new ApiError(message, response.status, codes);
	}

	if (response.status === 204) {
		return undefined as T;
	}

	const contentType = response.headers.get('content-type') ?? '';
	if (!contentType.includes('application/json')) {
		const text = await response.text();
		if (text.trim().length === 0) {
			return undefined as T;
		}
		throw new ApiError('Сервер вернул ответ в неожиданном формате.', response.status);
	}

	try {
		return (await response.json()) as T;
	} catch {
		throw new ApiError('Не удалось разобрать ответ сервера.', response.status);
	}
}
