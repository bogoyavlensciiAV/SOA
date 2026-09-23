<script lang="ts">
    interface Bar {
        label: string;
        value: number;
    }

    let { bars, maxValue, height = 160 }: { bars: Bar[]; maxValue?: number; height?: number } =
        $props();

    const computedMax = $derived(
        maxValue ?? Math.max(1000, ...bars.map((b) => b.value))
    );

    // Отступы под подпись значения сверху и подпись label снизу
    const valueLabelHeight = 24;
    const xLabelHeight = 36;
    const barAreaHeight = $derived(Math.max(0, height - valueLabelHeight - xLabelHeight));

    const ticks = $derived(
        [0, 0.25, 0.5, 0.75, 1].map((f) => Math.round(computedMax * f)).reverse()
    );

    function barHeightPx(value: number) {
        if (computedMax <= 0) return 2;
        const px = (value / computedMax) * barAreaHeight;
        return Math.max(2, px);
    }
</script>

<div class="flex gap-3" style="height: {height}px">
    <div
        class="flex flex-col justify-between text-right text-xs text-slate-400"
        style="height: {barAreaHeight}px; margin-top: {valueLabelHeight}px"
    >
        {#each ticks as tick}
            <span>{tick}</span>
        {/each}
    </div>

    <div class="relative flex flex-1 gap-4 border-l border-slate-200 pl-4" style="height: {height}px">
        <div
            class="pointer-events-none absolute inset-x-0 flex flex-col justify-between"
            style="top: {valueLabelHeight}px; height: {barAreaHeight}px"
        >
            {#each ticks as _}
                <div class="border-t border-slate-100"></div>
            {/each}
        </div>

        {#each bars as bar (bar.label)}
            <div class="relative flex w-full flex-col items-center justify-end gap-2">
                <span class="text-sm font-semibold text-slate-700">{bar.value}</span>
                <div
                    class="w-14 rounded-t-md bg-sky-500 transition-[height] duration-300"
                    style="height: {barHeightPx(bar.value)}px"
                    role="img"
                    aria-label="{bar.label}: {bar.value}"
                ></div>
                <span class="max-w-[6rem] truncate text-xs text-slate-500 mb-4">{bar.label}</span>
            </div>
        {/each}
    </div>
</div>