<script lang="ts">
	import type { Snippet } from 'svelte';

	let {
		open,
		title,
		onClose,
		widthClass = 'max-w-xl',
		children,
		footer
	}: {
		open: boolean;
		title: string;
		onClose: () => void;
		widthClass?: string;
		children: Snippet;
		footer?: Snippet;
	} = $props();

	function onKeydown(event: KeyboardEvent) {
		if (event.key === 'Escape') onClose();
	}
</script>

<svelte:window onkeydown={open ? onKeydown : undefined} />

{#if open}
	<div class="fixed inset-0 z-50 flex items-center justify-center p-4">
		<button
			type="button"
			aria-label="Закрыть"
			class="absolute inset-0 bg-slate-900/50"
			onclick={onClose}
		></button>

		<div
			class="relative z-10 flex max-h-[90vh] w-full {widthClass} flex-col overflow-hidden rounded-lg bg-white shadow-xl"
			role="dialog"
			aria-modal="true"
			aria-label={title}
		>
			<div class="flex items-center justify-between border-b border-slate-200 px-5 py-4">
				<h2 class="text-base font-semibold text-slate-900">{title}</h2>
				<button
					type="button"
					onclick={onClose}
					aria-label="Закрыть"
					class="rounded-md p-1 text-slate-400 hover:bg-slate-100 hover:text-slate-600"
				>
					<svg class="h-5 w-5" viewBox="0 0 20 20" fill="none">
						<path d="M5 5l10 10M15 5 5 15" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" />
					</svg>
				</button>
			</div>

			<div class="overflow-y-auto px-5 py-4">
				{@render children()}
			</div>

			{#if footer}
				<div class="flex justify-end gap-2 border-t border-slate-200 px-5 py-3">
					{@render footer()}
				</div>
			{/if}
		</div>
	</div>
{/if}
