<template>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="m-0">My Pets</h3>
    <router-link class="btn btn-primary" :to="{ name: 'pet-new' }">Add Pet</router-link>
  </div>

  <div v-if="loading" class="alert alert-info">Loading…</div>
  <div v-if="error" class="alert alert-danger">{{ error }}</div>

  <table v-if="pets.length" class="table table-striped align-middle">
    <thead>
      <tr>
        <th>Name</th>
        <th>Identification #</th>
        <th>Type</th>
        <th>Fur Color</th>
        <th>Country</th>
        <th style="width: 160px;"></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="p in pets" :key="p.id">
        <td>{{ p.name }}</td>
        <td>{{ p.identificationNumber }}</td>
        <td>{{ p.typeName }}</td>
        <td>{{ p.furColor }}</td>
        <td>{{ p.countryName || '—' }}</td>
        <td class="text-end">
          <router-link class="btn btn-sm btn-outline-secondary me-2"
                       :to="{ name: 'pet-edit', params: { id: p.id } }">Edit</router-link>
          <button class="btn btn-sm btn-outline-danger" @click="onDelete(p.id)">Delete</button>
        </td>
      </tr>
    </tbody>
  </table>

  <div v-else-if="!loading" class="text-muted">No pets yet. Click “Add Pet”.</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { listPets, deletePet } from '../api/pets';

const pets = ref([]);
const loading = ref(true);
const error = ref('');

const fetchPets = async () => {
  loading.value = true;
  error.value = '';
  try {
    const res = await listPets();
    pets.value = res.data;
  } catch (e) {
    error.value = 'Failed to load pets.';
  } finally {
    loading.value = false;
  }
};

const onDelete = async (id) => {
  if (!confirm('Delete this pet?')) return;
  try {
    await deletePet(id);
    await fetchPets();
  } catch (e) {
    alert('Failed to delete.');
  }
};

onMounted(fetchPets);
</script>