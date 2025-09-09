<template>
  <div v-if="open" class="pm-overlay" @click.self="onCancel">
    <div class="card shadow" style="min-width: 360px; max-width: 90%;">
      <div class="card-body">
        <h5 class="card-title mb-2">{{ title }}</h5>
        <p class="mb-3 text-muted">{{ message }}</p>
        <div class="d-flex justify-content-end gap-2">
          <button class="btn btn-outline-secondary" @click="onCancel">Cancel</button>
          <button class="btn btn-danger" @click="onOk">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  open: Boolean, title: { type: String, default: 'Confirm' },
  message: { type: String, default: 'Are you sure?' }
});
const emit = defineEmits(['ok','cancel']);
const onOk = () => emit('ok');
const onCancel = () => emit('cancel');
</script>