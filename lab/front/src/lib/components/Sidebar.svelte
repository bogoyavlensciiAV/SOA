<script lang="ts">
	import { page } from '$app/state';

	const links = [
		{ href: '/', label: 'Study Groups', icon: 'table' as const },
		{ href: '/statistics/', label: 'Statistics', icon: 'chart' as const },
		{ href: '/isu/', label: 'ISU', icon: 'swap' as const }
	];

	function isActive(href: string): boolean {
		if (href === '/') return page.url.pathname === '/';
		return page.url.pathname.startsWith(href);
	}
</script>

<nav class="flex h-full w-full flex-col border-r border-slate-200 bg-white text-slate-700">
	<div class="flex items-center gap-2 border-b border-slate-200 px-5 py-5">
		<div class="leading-tight">
			<p class="text-sm font-semibold text-slate-900">SOA Lab</p>
		</div>
	</div>

	<ul class="flex-1 space-y-1 px-3 py-4">
		{#each links as link (link.href)}
			<li>
				<a
					href={link.href}
					class="flex items-center gap-3 rounded-md px-3 py-2 text-sm font-medium transition-colors
						{isActive(link.href)
						? 'bg-[#FF00FF]/15 text-[#FF00FF]'
						: 'text-slate-600 hover:bg-slate-100 hover:text-slate-900'}"
				>
					<span class="flex h-5 w-5 shrink-0 items-center justify-center">
						{#if link.icon === 'table'}
							<svg viewBox="0 0 20 20" fill="none" class="h-5 w-5">
								<rect x="2.5" y="3.5" width="15" height="13" rx="1.5" stroke="currentColor" stroke-width="1.4" />
								<path d="M2.5 8h15M7.5 3.5v13" stroke="currentColor" stroke-width="1.4" />
							</svg>
						{:else if link.icon === 'chart'}
							<svg viewBox="0 0 20 20" fill="none" class="h-5 w-5">
								<path d="M4 16V9M10 16V4M16 16v-6" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" />
								<path d="M2.5 16.5h15" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" />
							</svg>
						{:else}
							<svg viewBox="0 0 20 20" fill="none" class="h-5 w-5">
								<path
									d="M3 7h11.5M14.5 7 11 3.5M17 13H5.5M5.5 13 9 16.5"
									stroke="currentColor"
									stroke-width="1.5"
									stroke-linecap="round"
									stroke-linejoin="round"
								/>
							</svg>
						{/if}
					</span>
					{link.label}
				</a>
			</li>
		{/each}
	</ul>
</nav>
