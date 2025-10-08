<template>
  <div class="relative rounded-2xl border border-slate-200 p-6 bg-white elev-1">
    <div class="absolute -top-4 left-6 h-10 w-10 rounded-full bg-blue-600 text-white grid place-items-center font-semibold elev-2">{{ number }}</div>
    <h3 class="text-lg font-medium text-blue-900 mt-6">{{ title }}</h3>
    <p class="mt-2 text-slate-700">{{ desc }}</p>
  </div>
</template>

<script setup lang="ts">
defineProps<{ number: string; title: string; desc: string }>()
</script>


