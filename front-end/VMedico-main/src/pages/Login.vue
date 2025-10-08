<template>
  <section class="min-h-screen bg-gradient-to-b from-blue-50 to-white py-12">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 grid md:grid-cols-2 gap-8 items-center">
      <!-- Left Side -->
      <div class="max-w-xl">
        <h1 class="text-4xl sm:text-5xl font-bold text-blue-900 inline-flex items-center gap-3 tracking-tight">
          <span class="material-symbols-rounded text-blue-700 text-3xl" style="font-variation-settings:'OPSZ' 28, 'wght' 700">medical_services</span>
          <span>Welcome to VMedico</span>
        </h1>
        <p class="text-slate-700 mt-2 text-lg">Your trusted partner in digital healthcare solutions</p>
        <ul class="mt-6 space-y-3 text-slate-800">
          <li class="flex items-center gap-4 leading-7 text-lg">
            <span class="material-symbols-rounded text-green-600 text-2xl">verified</span>
            <span class="font-semibold">Secure & HIPAA Compliant</span>
          </li>
          <li class="flex items-center gap-4 leading-7 text-lg">
            <span class="material-symbols-rounded text-green-600 text-2xl">schedule</span>
            <span class="font-semibold">24/7 Healthcare Access</span>
          </li>
          <li class="flex items-center gap-4 leading-7 text-lg">
            <span class="material-symbols-rounded text-green-600 text-2xl">science</span>
            <span class="font-semibold">Integrated Lab Results</span>
          </li>
        </ul>
      </div>

      <!-- Right Side (Login Card) -->
      <div class="bg-white border border-slate-200 rounded-xl p-6 shadow md:mt-0">
        <h2 class="text-lg font-semibold text-slate-900 inline-flex items-center gap-2">
          <span class="material-symbols-rounded text-blue-700">lock</span>
          <span>Access Your Account</span>
        </h2>
        <p class="text-slate-600 mt-1">Login to your VMedico account</p>

        <div class="mt-4 space-y-2">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-2">
            <button type="button" @click="role='patient'" :aria-pressed="role==='patient'"
              class="w-full rounded-lg border px-4 py-3 text-left focus:outline-none focus:ring-2 focus:ring-blue-400"
              :class="role==='patient' ? 'bg-blue-50 border-blue-600' : 'hover:bg-slate-50'">
              <div class="font-medium inline-flex items-center gap-2">
                <span class="material-symbols-rounded text-blue-700">person</span> 
                <span>Patient</span>
              </div>
            </button>

            <button type="button" @click="role='lab'" :aria-pressed="role==='lab'"
              class="w-full rounded-lg border px-4 py-3 text-left focus:outline-none focus:ring-2 focus:ring-blue-400"
              :class="role==='lab' ? 'bg-blue-50 border-blue-600' : 'hover:bg-slate-50'">
              <div class="font-medium inline-flex items-center gap-2">
                <span class="material-symbols-rounded text-blue-700">science</span> 
                <span>Lab</span>
              </div>
            </button>

            <button type="button" @click="role='corporate'" :aria-pressed="role==='corporate'"
              class="w-full rounded-lg border px-4 py-3 text-left focus:outline-none focus:ring-2 focus:ring-blue-400"
              :class="role==='corporate' ? 'bg-blue-50 border-blue-600' : 'hover:bg-slate-50'">
              <div class="font-medium inline-flex items-center gap-2">
                <span class="material-symbols-rounded text-blue-700">domain</span> 
                <span>Corporate</span>
              </div>
            </button>
          </div>
        </div>

        <form class="mt-6 space-y-4" @submit.prevent="loginUser">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">
              <span class="inline-flex items-center gap-1">
                <span class="material-symbols-rounded text-blue-700">account_circle</span>
                <span>Username or Email</span>
              </span>
            </label>
            <input v-model="usernameOrEmail" required type="text" placeholder="Enter your username or email" class="form-input h-12 leading-6 w-full border rounded-md p-2" />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">
              <span class="inline-flex items-center gap-1">
                <span class="material-symbols-rounded text-blue-700">lock</span>
                <span>Password</span>
              </span>
            </label>
            <input v-model="password" required type="password" placeholder="Enter your password" class="form-input h-12 leading-6 w-full border rounded-md p-2" />
          </div>

          <button type="submit" class="btn-primary w-full inline-flex items-center justify-center gap-2 bg-blue-700 text-white py-3 rounded-lg hover:bg-blue-800 transition">
            <span class="material-symbols-rounded">login</span>
            <span>Login</span>
          </button>

          <p class="text-sm text-slate-700 text-center">Don't have an account? 
            <router-link to="/register" class="text-blue-700 font-medium hover:underline">Register here</router-link>
          </p>
        </form>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const role = ref<'patient' | 'lab' | 'corporate'>('patient')
const usernameOrEmail = ref('')
const password = ref('')

const BASE_URL = 'http://localhost:8080/api/login'

const loginUser = async () => {
  try {
    const response = await axios.post(BASE_URL, {
      usernameOrEmail: usernameOrEmail.value,
      password: password.value
    })

    alert(response.data.message)
    // Redirect to dummy dashboard after success
    router.push('/dashboard')
  } catch (error: any) {
    alert(error.response?.data?.message || 'Invalid credentials. Please try again.')
  }
}
</script>
