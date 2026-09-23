import { toast } from 'svelte-sonner';
import { ApiError } from '$lib/api/httpClient';

export function toastSuccess(message: string): void {
	toast.success(message);
}

/** Shows a toast for any error, normalizing `ApiError` vs. unknown errors. */
export function toastError(error: unknown, fallback = 'Произошла ошибка.'): void {
	if (error instanceof ApiError) {
		toast.error(error.message);
		return;
	}
	if (error instanceof Error) {
		toast.error(error.message || fallback);
		return;
	}
	toast.error(fallback);
}
