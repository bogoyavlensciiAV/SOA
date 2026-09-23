<script lang="ts">
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import Sidebar from '$lib/components/Sidebar.svelte';
	import { Toaster } from 'svelte-sonner';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>ITMO Students — Study Groups Admin</title>
</svelte:head>

<Toaster position="top-right" richColors closeButton />

<div class="flex h-screen w-screen overflow-hidden bg-slate-50">
	<aside class="hidden w-64 shrink-0 md:block">
		<Sidebar />
	</aside>

	<div class="flex min-w-0 flex-1 flex-col overflow-hidden">
		<header class="flex items-center justify-between border-b border-slate-200 bg-white px-4 py-3 md:hidden">
			<span class="text-sm font-semibold text-slate-900">ITMO Students</span>
		</header>

		<div class="border-b border-slate-200 bg-white px-4 py-2 md:hidden">
			<div class="flex gap-2 overflow-x-auto text-sm">
				<a href="/" class="rounded-md px-3 py-1.5 font-medium text-slate-700 hover:bg-slate-100">Study Groups</a>
				<a href="/statistics/" class="rounded-md px-3 py-1.5 font-medium text-slate-700 hover:bg-slate-100">Statistics</a>
				<a href="/isu/" class="rounded-md px-3 py-1.5 font-medium text-slate-700 hover:bg-slate-100">ISU</a>
			</div>
		</div>

		<main class="flex-1 overflow-y-auto p-4 md:p-6">
			{@render children()}
		</main>
	</div>
</div>
