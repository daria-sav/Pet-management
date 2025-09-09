<template>
  <div class="container py-4">
    <div class="row justify-content-center">
      <div class="col-12" style="max-width: 420px;">
        <div class="card shadow-sm">
          <div class="card-body">
            <h4 class="mb-3 text-center">Sign in</h4>

            <form @submit.prevent="onSubmit" novalidate>
              <div class="mb-3">
                <label class="form-label">Username</label>
                <input v-model.trim="username" class="form-control" autocomplete="username" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Password</label>
                <input v-model="password" type="password" class="form-control" autocomplete="current-password" required />
              </div>

              <button class="btn btn-primary w-100" :disabled="loading">
                {{ loading ? 'Signing in…' : 'Sign in' }}
              </button>

              <p v-if="error" class="text-danger mt-3">{{ error }}</p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useAuthStore } from '../stores/auth';
import { useRouter } from 'vue-router';
import { useToasts } from '../stores/toasts';

const username = ref('');
const password = ref('');
const loading = ref(false);
const error = ref('');
const auth = useAuthStore();
const router = useRouter();
const toast = useToasts();

const onSubmit = async () => {
  error.value = '';
  loading.value = true;
  try {
    await auth.login(username.value, password.value);
    router.push({ name: 'pets' });
  } catch (e) {
    error.value = 'Invalid credentials';
    toast.error('Invalid credentials');
  } finally {
    loading.value = false;
  }
};
</script>
