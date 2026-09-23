import { PUBLIC_API_BASE_URL } from '$env/static/public';

export const API_BASE_URL: string =
	(PUBLIC_API_BASE_URL && PUBLIC_API_BASE_URL.trim()) || 'http://localhost:8080';

export const ISU_BASE_URL: string = 'http://localhost:40811'

export const DEFAULT_PAGE_SIZE = 25;
