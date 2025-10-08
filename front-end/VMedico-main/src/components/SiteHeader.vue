<template>
  <header class="sticky top-0 z-50 bg-white/90 backdrop-blur border-b border-slate-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex h-16 items-center justify-between">
      <router-link to="/" class="flex items-center gap-2 group">
        <svg viewBox="0 0 48 48" aria-label="VMedico" class="h-9 w-9 rounded-lg elev-2 text-blue-700">
          <circle cx="24" cy="24" r="22" stroke="currentColor" stroke-width="2" opacity=".15" fill="none" />
          <rect x="14" y="21" width="20" height="6" rx="2" fill="currentColor" />
          <rect x="21" y="14" width="6" height="20" rx="2" fill="currentColor" />
        </svg>
        <span class="text-xl font-medium text-black group-hover:text-black">VMedico</span>
      </router-link>
      <div class="flex items-center gap-3">
        <nav class="hidden md:flex items-center gap-6 mr-2">
          <router-link to="/" class="text-slate-700 hover:text-blue-700 font-medium flex items-center gap-1">
            <span class="material-symbols-rounded text-blue-700" style="font-variation-settings:'OPSZ' 24, 'wght' 500" aria-hidden="true">home</span>
            <span>Home</span>
          </router-link>
          <router-link to="/about" class="text-slate-700 hover:text-blue-700 font-medium flex items-center gap-1">
            <span class="material-symbols-rounded text-blue-700" style="font-variation-settings:'OPSZ' 24, 'wght' 500" aria-hidden="true">info</span>
            <span>About</span>
          </router-link>
        </nav>
        <router-link to="/login" class="btn-primary flex items-center gap-1">
          <span class="material-symbols-rounded" style="font-variation-settings:'OPSZ' 24, 'wght' 600" aria-hidden="true">login</span>
          <span>Login</span>
        </router-link>
        <router-link to="/register" class="btn-primary flex items-center gap-1">
          <span class="material-symbols-rounded" style="font-variation-settings:'OPSZ' 24, 'wght' 600" aria-hidden="true">how_to_reg</span>
          <span>Register</span>
        </router-link>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
</script>


