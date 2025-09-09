<template>
  <nav class="navbar navbar-expand navbar-dark bg-dark">
    <div class="container">
      <RouterLink to="/" class="navbar-brand">Pet Management</RouterLink>

      <ul class="navbar-nav ms-auto" v-if="auth.token">
        <li class="nav-item me-3 d-flex align-items-center text-white-50">
          Hello, {{ auth.username }}
        </li>
        <li class="nav-item">
          <button class="btn btn-outline-light btn-sm" @click="onLogout">Logout</button>
        </li>
      </ul>
    </div>
  </nav>

  <main class="container py-4">
    <RouterView />
  </main>

  <Toasts />
</template>

<script setup>
import { useAuthStore } from './stores/auth';
import { useRouter, RouterLink, RouterView } from 'vue-router';
import Toasts from './ui/Toasts.vue';

const auth = useAuthStore();
const router = useRouter();

const onLogout = () => {
  auth.logout();
  router.push({ name: 'login' });
};
</script>