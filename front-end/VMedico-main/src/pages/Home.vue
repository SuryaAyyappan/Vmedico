<template>
  <div>
    <!-- Hero -->
    <section class="relative overflow-hidden">
      <div class="absolute inset-0 bg-gradient-to-b from-blue-50 to-white"></div>
      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div class="grid md:grid-cols-2 gap-10 items-center">
          <div>
            <h1 class="text-4xl sm:text-5xl font-bold text-blue-900 leading-tight">Your Health,<br />Connected</h1>
            <p class="mt-5 text-base sm:text-lg text-slate-700 max-w-xl">
              VMedico bridges the gap between patients, doctors, and healthcare providers through innovative telemedicine solutions and integrated health monitoring.
            </p>
            <div class="mt-8 flex flex-wrap gap-3">
              <router-link to="/login" class="btn-primary">Get Started</router-link>
              <router-link to="/about" class="btn-outline">Learn More</router-link>
            </div>
          </div>
          <!-- Hero image removed -->
        </div>
        <!-- Inline stats under hero -->
        <div class="mt-10 grid grid-cols-2 md:grid-cols-4 gap-6">
          <div class="text-center">
            <div class="text-xl font-medium text-blue-900">500+</div>
            <div class="text-slate-600 mt-1">Healthcare Providers</div>
          </div>
          <div class="text-center">
            <div class="text-xl font-medium text-blue-900">10,000+</div>
            <div class="text-slate-600 mt-1">Patients Served</div>
          </div>
          <div class="text-center">
            <div class="text-xl font-medium text-blue-900">25,000+</div>
            <div class="text-slate-600 mt-1">Consultations</div>
          </div>
          <div class="text-center">
            <div class="text-xl font-medium text-blue-900">100+</div>
            <div class="text-slate-600 mt-1">Lab Partners</div>
          </div>
        </div>
      </div>
    </section>
    
    <!-- Remove separate band; stats shown inline above -->

    <!-- Services -->
    <section id="services" class="py-20">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-3xl font-bold text-blue-900 text-center">Comprehensive Healthcare Solutions</h2>
        <p class="mt-3 text-slate-700 max-w-3xl mx-auto text-center">Our platform integrates multiple healthcare services to provide seamless care coordination between patients, doctors, and medical facilities.</p>

        <div class="mt-10 grid md:grid-cols-3 gap-6">
          <FeatureCard icon="video_call" title="Tele-Consulting" desc="Connect with specialized medical practitioners from anywhere" />
          <FeatureCard icon="event" title="Appointment Management" desc="Schedule appointments and receive automated reminders" />
          <FeatureCard icon="science" title="Lab Integration" desc="Track laboratory results and monitor health details" />
          <FeatureCard icon="prescriptions" title="Digital Prescriptions" desc="Manage prescriptions and medication reminders" />
          <FeatureCard icon="notifications_active" title="Smart Notifications" desc="Automated email notifications for doctors and patients" />
          <FeatureCard icon="encrypted" title="Secure Platform" desc="HIPAA compliant with enterprise-grade security" />
        </div>
      </div>
    </section>

    <!-- Audience section removed -->

    <!-- How it works -->
    <section class="py-20">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-3xl font-bold text-blue-900 text-center">How VMedico Works</h2>
        <p class="mt-3 text-slate-700 text-center">Simple steps to better healthcare</p>
        <div class="mt-10 grid md:grid-cols-3 gap-6">
          <StepCard number="1" title="Register & Verify" desc="Create your account and verify your identity for secure access" />
          <StepCard number="2" title="Find Your Doctor" desc="Browse specialists and book appointments that fit your schedule" />
          <StepCard number="3" title="Consult & Monitor" desc="Have video consultations, receive prescriptions, and track your health" />
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import FeatureCard from '../sections/FeatureCard.vue'
import AudienceCard from '../sections/AudienceCard.vue'
import StepCard from '../sections/StepCard.vue'
</script>


