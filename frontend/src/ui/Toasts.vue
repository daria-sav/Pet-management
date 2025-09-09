<template>
  <div class="position-fixed top-0 end-0 p-3" style="z-index: 1080;">
    <div v-for="t in toasts.items" :key="t.id"
         class="toast show align-items-center text-bg-light border-0 mb-2 shadow">
      <div :class="['toast-body', `border-start`, `border-4`, borderColor(t.variant)]">
        <strong class="me-2">{{ t.title }}</strong>
        <span v-if="t.message">{{ t.message }}</span>
        <button type="button" class="btn-close float-end" @click="toasts.dismiss(t.id)"></button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useToasts } from '../stores/toasts';
const toasts = useToasts();
const borderColor = (v) =>
  v === 'success' ? 'border-success'
: v === 'danger'  ? 'border-danger'
: v === 'info'    ? 'border-info'
: 'border-secondary';
</script>
