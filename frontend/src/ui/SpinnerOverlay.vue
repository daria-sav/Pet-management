<template>
  <div v-if="show" class="pm-overlay">
    <div class="spinner-border" role="status" aria-label="loading"></div>
  </div>
</template>
<script setup>
defineProps({ show: Boolean });
</script>