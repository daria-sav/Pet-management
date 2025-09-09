<template>
  <div class="pm-empty">
    <span class="icon">🐾</span>
    <h5 class="mb-1">{{ title }}</h5>
    <p class="mb-3">{{ subtitle }}</p>
    <slot></slot>
  </div>
</template>
<script setup>
defineProps({
  title: { type: String, default: 'Nothing here yet' },
  subtitle: { type: String, default: 'Add your first item to get started.' }
});
</script>