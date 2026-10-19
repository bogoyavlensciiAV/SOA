import { request } from './httpClient';
import type { LhsDefinition } from '$lib/types/api';

export function getLhsDefinitions(): Promise<LhsDefinition[]> {
	return request<LhsDefinition[]>('/study-groups/lhs-definitions', { method: 'GET' });
}