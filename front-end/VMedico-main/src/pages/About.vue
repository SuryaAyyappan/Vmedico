<template>
  <section>
    <div class="relative overflow-hidden">
      <div class="absolute inset-0 bg-gradient-to-b from-blue-50 to-white"></div>
      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div class="grid md:grid-cols-2 gap-10 items-center">
          <div>
            <h1 class="text-4xl font-bold text-blue-900">Transforming Healthcare Through Innovation</h1>
            <p class="mt-4 text-lg text-slate-700 max-w-3xl">VMedico is revolutionizing healthcare delivery by connecting patients, doctors, hospitals, and laboratories through a unified digital platform. Our mission is to make quality healthcare accessible to everyone, everywhere.</p>
            <div class="mt-6 flex gap-4">
              <router-link to="/login" class="btn-primary">Join Our Mission</router-link>
        
            </div>
          </div>
          <!-- About page image removed -->
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
        <div class="text-center">
          <div class="text-xl font-medium text-blue-900">10M+</div>
          <div class="text-slate-600 mt-1">Patients Served</div>
        </div>
        <div class="text-center">
          <div class="text-xl font-medium text-blue-900">5,000+</div>
          <div class="text-slate-600 mt-1">Healthcare Providers</div>
        </div>
        <div class="text-center">
          <div class="text-xl font-medium text-blue-900">100+</div>
          <div class="text-slate-600 mt-1">Partner Laboratories</div>
        </div>
        <div class="text-center">
          <div class="text-xl font-medium text-blue-900">50+</div>
          <div class="text-slate-600 mt-1">Cities Covered</div>
        </div>
      </div>

      <!-- Mission and Vision sections removed -->

      <div class="mt-16">
        <h2 class="text-2xl font-bold text-blue-900 inline-flex items-center gap-2">
          <span class="material-symbols-rounded text-blue-700" style="font-variation-settings:'OPSZ' 24, 'wght' 600" aria-hidden="true">grade</span>
          <span>Our Core Values</span>
        </h2>
        <div class="mt-6 grid md:grid-cols-2 gap-6">
          <FeatureCard icon="favorite" title="Patient-Centered Care" desc="Every decision we make puts patients first, ensuring accessible, quality healthcare for all." />
          <FeatureCard icon="lightbulb" title="Innovation Excellence" desc="We leverage cutting-edge technology to transform healthcare delivery and improve outcomes." />
          <FeatureCard icon="security" title="Trusted Security" desc="Your health information is protected with enterprise-grade security and HIPAA compliance." />
          <FeatureCard icon="verified" title="Quality Assurance" desc="We maintain the highest standards through rigorous certification and continuous improvement." />
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import StatCard from '../sections/StatCard.vue'
import FeatureCard from '../sections/FeatureCard.vue'
</script>


