<template>
  <footer class="mt-24 bg-[#0f1b2a] text-slate-200">
    <div class="max-w-7xl mx-auto px-6 lg:px-8 py-14 grid grid-cols-1 md:grid-cols-4 gap-10">
      <!-- Brand -->
      <div>
        <div class="flex items-center gap-3 mb-4">
          <svg viewBox="0 0 48 48" aria-label="VMedico" class="h-9 w-9 rounded-lg elev-2 text-blue-700 bg-white">
            <circle cx="24" cy="24" r="22" stroke="currentColor" stroke-width="2" opacity=".15" fill="none" />
            <rect x="14" y="21" width="20" height="6" rx="2" fill="currentColor" />
            <rect x="21" y="14" width="6" height="20" rx="2" fill="currentColor" />
          </svg>
          <span class="text-2xl font-medium text-slate-100">VMedico</span>
        </div>
        <p class="text-slate-300 max-w-sm">Connecting healthcare providers and patients through innovative technology.</p>
      </div>

      <!-- Platform -->
      <div>
        <h3 class="text-slate-100 font-medium mb-3">Platform</h3>
        <ul class="space-y-2 text-slate-300">
          <li><a href="#" class="hover:text-white">Services</a></li>
          <li><a href="#" class="hover:text-white">Find Doctors</a></li>
          <li><a href="#" class="hover:text-white">Laboratories</a></li>
          <li><router-link to="/about" class="hover:text-white">About Us</router-link></li>
        </ul>
      </div>

      <!-- Support -->
      <div>
        <h3 class="text-slate-100 font-medium mb-3">Support</h3>
        <ul class="space-y-2 text-slate-300">
          <li><a href="#" class="hover:text-white">Help Center</a></li>
          <li><a href="#" class="hover:text-white">Contact Us</a></li>
          <li><a href="#" class="hover:text-white">Privacy Policy</a></li>
          <li><a href="#" class="hover:text-white">Terms of Service</a></li>
        </ul>
      </div>

      <!-- Contact -->
      <div>
        <h3 class="text-slate-100 font-medium mb-3">Contact</h3>
        <ul class="space-y-2 text-slate-300">
          <li>111-001-VMEDICO</li>
          <li>Supportvmedico@vmedico.com</li>
          <li>24/7 Emergency Line</li>
        </ul>
      </div>
    </div>
    <div class="border-t border-white/10 py-6 text-center text-slate-400 text-sm">© 2025 VMedico. All rights reserved.</div>
  </footer>
</template>

<script setup lang="ts"></script>


