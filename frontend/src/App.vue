<template>
  <nav class="navbar navbar-expand navbar-dark bg-dark">
    <div class="container">
      <a class="navbar-brand" href="/">Pet Management</a>
      <ul class="navbar-nav ms-auto" v-if="auth.token">
        <li class="nav-item me-3 text-white-50">Hello, {{ auth.username }}</li>
        <li class="nav-item">
          <button class="btn btn-outline-light btn-sm" @click="onLogout">Logout</button>
        </li>
      </ul>
    </div>
  </nav>
  <main class="container py-4">
    <router-view />
  </main>
</template>

<script setup>
import { useAuthStore } from './stores/auth';
import { useRouter } from 'vue-router';

const auth = useAuthStore();
const router = useRouter();

const onLogout = () => {
  auth.logout();
  router.push({ name: 'login' });
};
</script>

<style>
body { background: #f8f9fa; }
</style>