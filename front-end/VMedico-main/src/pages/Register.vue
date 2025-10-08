<template>
  <section class="py-16 bg-slate-50">
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
      <h1 class="text-3xl font-bold text-blue-900 inline-flex items-center gap-2">
        <span class="material-symbols-rounded text-blue-700" style="font-variation-settings:'OPSZ' 24, 'wght' 600">
          person_add
        </span>
        <span>Join VMedico</span>
      </h1>
      <p class="text-slate-700 mt-2">
        Create your account to access our healthcare platform
      </p>

      <!-- Select Role -->
      <div class="mt-6">
        <label class="block text-sm font-medium text-slate-700 mb-1">
          <span class="inline-flex items-center gap-1">
            <span class="material-symbols-rounded text-blue-700 align-middle"
              style="font-variation-settings:'OPSZ' 20, 'wght' 600">group</span>
            <span>Select Role</span>
          </span>
        </label>
        <select v-model="role"
          class="mt-1 w-full md:w-80 rounded-lg border border-slate-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400">
          <option value="patient">Patient</option>
          <option value="lab">Lab</option>
          <option value="corporate">Corporate</option>
        </select>
      </div>

      <!-- Registration Form -->
      <form @submit.prevent="register" class="mt-8 grid md:grid-cols-2 gap-6">

        <!-- Shared Fields -->
        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">
            <span class="inline-flex items-center gap-1">
              <span class="material-symbols-rounded text-blue-700 align-middle"
                style="font-variation-settings:'OPSZ' 20, 'wght' 600">account_circle</span>
              <span>Username</span>
            </span>
          </label>
          <input v-model="form.username" type="text" placeholder="Enter username" required
            class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">
            <span class="inline-flex items-center gap-1">
              <span class="material-symbols-rounded text-blue-700 align-middle"
                style="font-variation-settings:'OPSZ' 20, 'wght' 600">lock</span>
              <span>Password</span>
            </span>
          </label>
          <input v-model="form.password" type="password" placeholder="Enter password" required
            class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">
            <span class="inline-flex items-center gap-1">
              <span class="material-symbols-rounded text-blue-700 align-middle"
                style="font-variation-settings:'OPSZ' 20, 'wght' 600">mail</span>
              <span>Email</span>
            </span>
          </label>
          <input v-model="form.email" type="email" placeholder="Enter email" required
            class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">
            <span class="inline-flex items-center gap-1">
              <span class="material-symbols-rounded text-blue-700 align-middle"
                style="font-variation-settings:'OPSZ' 20, 'wght' 600">call</span>
              <span>Phone Number</span>
            </span>
          </label>
          <input v-model="form.phoneNumber" type="tel" placeholder="Enter phone number" required
            class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
        </div>

        <!-- Patient Fields -->
        <template v-if="role === 'patient'">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Full Name</label>
            <input v-model="form.name" type="text" placeholder="Enter full name"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Date of Birth</label>
            <input v-model="form.dob" type="date"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Gender</label>
            <select v-model="form.gender"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400">
              <option>Male</option>
              <option>Female</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Blood Group</label>
            <select v-model="form.bloodGroup"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400">
              <option>O+</option>
              <option>O-</option>
              <option>A+</option>
              <option>A-</option>
              <option>B+</option>
              <option>B-</option>
              <option>AB+</option>
              <option>AB-</option>
            </select>
          </div>

          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-slate-700 mb-1">Address</label>
            <input v-model="form.address" type="text" placeholder="Enter full address"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Emergency Contact</label>
            <input v-model="form.emergencyContact" type="tel" placeholder="Enter emergency contact"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>
        </template>

        <!-- Lab Fields -->
        <template v-if="role === 'lab'">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Lab Name</label>
            <input v-model="form.labName" type="text" placeholder="Enter lab name"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">License Number</label>
            <input v-model="form.licenseNumber" type="text" placeholder="Enter license number"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>
        </template>

        <!-- Corporate (Admin) Fields -->
        <template v-if="role === 'corporate'">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Full Name</label>
            <input v-model="form.name" type="text" placeholder="Enter your full name"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>


            <div>
    <label class="block text-sm font-medium text-slate-700 mb-1">Gender</label>
    <select v-model="form.gender"
      class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400">
      <option value="male">Male</option>
      <option value="female">Female</option>
    </select>
  </div>


   <div>
    <label class="block text-sm font-medium text-slate-700 mb-1">Date of Birth</label>
    <input v-model="form.dob" type="date"
      class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
  </div>

  <div>
    <label class="block text-sm font-medium text-slate-700 mb-1">Specialization</label>
    <input v-model="form.specialization" type="text" placeholder="e.g. Cardiologist"
      class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
  </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Hospital Name</label>
            <input v-model="form.hospitalName" type="text" placeholder="Enter hospital name"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Designation</label>
            <input v-model="form.designation" type="text" placeholder="Enter your designation"
              class="mt-1 w-full rounded-lg border border-slate-300 px-4 py-2 focus:ring-2 focus:ring-blue-400" />
          </div>
        </template>

        <!-- Submit -->
        <div class="md:col-span-2">
          <button type="submit"
            class="bg-blue-700 text-white font-semibold rounded-lg px-6 py-2 hover:bg-blue-800 transition">
            Register
          </button>
        </div>
      </form>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'

const role = ref<'patient' | 'lab' | 'corporate'>('patient')

const form = ref({
  username: '',
  password: '',
  email: '',
  phoneNumber: '',
  name: '',
  gender: '',
  dob: '',
  address: '',
  bloodGroup: '',
  emergencyContact: '',
  labName: '',
  licenseNumber: '',
  hospitalName: '', // ✅ Added for Corporate
  designation: '' // ✅ Added for Corporate
})

// ✅ Backend Base URL
const BASE_URL = 'http://localhost:8080/api/register'

const register = async () => {
  let url = ''
  let payload: any = {}

  if (role.value === 'patient') {
    url = `${BASE_URL}/patient`
    payload = {
      username: form.value.username,
      email: form.value.email,
      password: form.value.password,
      name: form.value.name,
      gender: form.value.gender,
      dob: form.value.dob,
      address: form.value.address,
      bloodGroup: form.value.bloodGroup,
      phoneNumber: form.value.phoneNumber,
      emergencyContact: form.value.emergencyContact
    }
  } else if (role.value === 'lab') {
    url = `${BASE_URL}/lab`
    payload = {
      username: form.value.username,
      email: form.value.email,
      password: form.value.password,
      labName: form.value.labName,
      licenseNumber: form.value.licenseNumber,
      phoneNumber: form.value.phoneNumber
    }
  } else if (role.value === 'corporate') {
    url = `${BASE_URL}/doctor`
    payload = {
      username: form.value.username,
      email: form.value.email,
      password: form.value.password,
      gender: form.value.gender,
      dob: form.value.dob,
      specialization: form.value.specialization,
      name: form.value.name,
      hospitalName: form.value.hospitalName,
      qualification: form.value.designation,
      phoneNumber: form.value.phoneNumber
    }
  }

  try {
    const res = await axios.post(url, payload)
    alert(res.data.message || 'Registration successful!')
  } catch (err: any) {
    alert(err.response?.data?.message || 'Registration failed.')
  }
}
</script>
