<template>
  <div class="rounded-2xl border border-slate-200 p-8 bg-white shadow-sm text-center">
    <div class="mx-auto h-16 w-16 rounded-full bg-blue-50 grid place-items-center mb-4">
      <span class="material-symbols-rounded text-blue-700 text-3xl" style="font-variation-settings:'OPSZ' 24, 'wght' 600" aria-hidden="true">{{ icon }}</span>
    </div>
    <h3 class="text-xl font-semibold text-blue-900">{{ title }}</h3>
    <p v-if="subtitle" class="text-slate-600 mt-1">{{ subtitle }}</p>
    <ul class="mt-6 space-y-3 text-slate-700 text-left max-w-md mx-auto">
      <li v-for="item in items" :key="item" class="flex items-start gap-2">
        <span class="material-symbols-rounded text-blue-700" style="font-variation-settings:'OPSZ' 24, 'wght' 600" aria-hidden="true">{{ getIconFor(item) }}</span>
        <span>{{ item }}</span>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
defineProps<{ title: string; items: string[]; icon?: string; subtitle?: string }>()

function getIconFor(item: string): string {
  const text = item.toLowerCase();
  if (text.includes('video')) return 'video_call';
  if (text.includes('prescription')) return 'prescriptions';
  if (text.includes('lab report') || text.includes('test result') || text.includes('lab result')) return 'science';
  if (text.includes('appointment')) return 'event';
  if (text.includes('quality')) return 'verified';
  if (text.includes('integration')) return 'hub';
  if (text.includes('multi-doctor') || text.includes('doctor')) return 'group';
  if (text.includes('patient database') || text.includes('database')) return 'folder_shared';
  if (text.includes('report')) return 'description';
  if (text.includes('admin') || text.includes('dashboard')) return 'dashboard';
  return 'check_circle';
}
</script>



