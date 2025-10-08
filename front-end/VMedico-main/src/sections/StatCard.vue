<template>
  <div class="p-2 text-center">
    <div :class="['font-extrabold text-blue-700', small ? 'text-4xl md:text-5xl' : 'text-5xl md:text-6xl']">{{ value }}</div>
    <div :class="['mt-2 text-slate-600', small ? 'text-base' : 'text-lg']">{{ label }}</div>
  </div>
</template>

<script setup lang="ts">
defineProps<{ value: string; label: string; small?: boolean }>()

// Intentionally minimal: premium text-only stat display
</script>


