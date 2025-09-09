<template>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="m-0">My Pets</h3>
    <router-link class="btn btn-primary" :to="{ name: 'pet-new' }">Add Pet</router-link>
  </div>

  <SpinnerOverlay :show="loading" />
  <div v-if="error" class="alert alert-danger">{{ error }}</div>

  <EmptyState
    v-if="!loading && !pets.length && !error"
    title="No pets yet"
    subtitle="Add your first pet to start managing your list."
  >
    <router-link class="btn btn-primary mt-2" :to="{ name:'pet-new' }">Add Pet</router-link>
  </EmptyState>

  <div v-else class="card">
    <div class="card-body p-0">
      <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th :aria-sort="ariaSort('name')" class="pm-th-sort" @click="toggleSort('name')">
                <span class="d-inline-flex align-items-center gap-1">
                  Name
                  <i :class="iconFor('name')" aria-hidden="true"></i>
                </span>
              </th>
              <th :aria-sort="ariaSort('identificationNumber')" class="pm-th-sort" @click="toggleSort('identificationNumber')">
                <span class="d-inline-flex align-items-center gap-1">
                  ID Number
                  <i :class="iconFor('identificationNumber')" aria-hidden="true"></i>
                </span>
              </th>
              <th :aria-sort="ariaSort('typeName')" class="pm-th-sort" @click="toggleSort('typeName')">
                <span class="d-inline-flex align-items-center gap-1">
                  Type
                  <i :class="iconFor('typeName')" aria-hidden="true"></i>
                </span>
              </th>
              <th :aria-sort="ariaSort('furColor')" class="pm-th-sort" @click="toggleSort('furColor')">
                <span class="d-inline-flex align-items-center gap-1">
                  Fur Color
                  <i :class="iconFor('furColor')" aria-hidden="true"></i>
                </span>
              </th>
              <th :aria-sort="ariaSort('countryName')" class="pm-th-sort" @click="toggleSort('countryName')">
                <span class="d-inline-flex align-items-center gap-1">
                  Country
                  <i :class="iconFor('countryName')" aria-hidden="true"></i>
                </span>
              </th>
              <th class="text-end" style="width: 160px;"></th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="p in sortedPets" :key="p.id">
              <td class="fw-medium">{{ p.name }}</td>
              <td><span class="pm-chip">{{ p.identificationNumber }}</span></td>
              <td>
                <span class="pm-dot" :style="{ background: typeColor(p.typeName) }"></span>
                {{ p.typeName }}
              </td>
              <td>{{ p.furColor }}</td>
              <td>{{ p.countryName || '—' }}</td>
              <td class="text-end pm-actions">
                <router-link class="btn btn-sm btn-outline-secondary"
                             :to="{ name: 'pet-edit', params: { id: p.id } }">Edit</router-link>
                <button class="btn btn-sm btn-outline-danger" @click="askDelete(p)">Delete</button>
              </td>
            </tr>
          </tbody>

        </table>
      </div>
    </div>
  </div>

  <ConfirmModal
    :open="confirmOpen"
    title="Delete pet"
    :message="`Delete '${toDelete?.name}'? This action cannot be undone.`"
    @ok="confirmDelete"
    @cancel="confirmOpen=false"
  />
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { listPets, deactivatePet } from '../api/pets';
import SpinnerOverlay from '../ui/SpinnerOverlay.vue';
import EmptyState from '../ui/EmptyState.vue';
import ConfirmModal from '../ui/ConfirmModal.vue';
import { useToasts } from '../stores/toasts';

const pets = ref([]);
const loading = ref(true);
const error = ref('');
const toast = useToasts();

const confirmOpen = ref(false);
const toDelete = ref(null);

const sort = ref({ key: 'name', dir: 'asc' }); // dir: 'asc' | 'desc'

function toggleSort(key) {
  if (sort.value.key === key) {
    sort.value.dir = sort.value.dir === 'asc' ? 'desc' : 'asc';
  } else {
    sort.value.key = key;
    sort.value.dir = 'asc';
  }
}

function ariaSort(key) {
  if (sort.value.key !== key) return 'none';
  return sort.value.dir === 'asc' ? 'ascending' : 'descending';
}

function iconFor(key) {
  if (sort.value.key !== key) return 'bi bi-arrow-down-up text-muted'; 
  return sort.value.dir === 'asc' ? 'bi bi-caret-up-fill' : 'bi bi-caret-down-fill';
}

function safeStr(v) { return (v ?? '').toString().toLowerCase(); }
function cmp(a, b, key) {
  const va = a[key];
  const vb = b[key];

  if (key === 'identificationNumber') {
    const na = /^\d+$/.test(String(va)) ? Number(va) : null;
    const nb = /^\d+$/.test(String(vb)) ? Number(vb) : null;
    if (na !== null && nb !== null) return na - nb;
  }

  const sa = safeStr(va);
  const sb = safeStr(vb);
  return sa.localeCompare(sb);
}

const sortedPets = computed(() => {
  const { key, dir } = sort.value;
  const arr = [...pets.value];
  arr.sort((a, b) => {
    const r = cmp(a, b, key);
    return dir === 'asc' ? r : -r;
  });
  return arr;
});

const typeColor = (name) =>
  name === 'Cat'   ? '#8b5cf6' :   
  name === 'Dog'   ? '#10b981' :   
  name === 'Other' ? '#f59e0b' :   
                     '#94a3b8'; 

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

const askDelete = (p) => { 
  toDelete.value = p; 
  confirmOpen.value = true; 
};

const confirmDelete = async () => {
  try {
    await deactivatePet (toDelete.value.id);
    toast.success('Pet deleted');
    await fetchPets();
  } catch {
    toast.error('Failed to delete');
  } finally {
    confirmOpen.value = false;
  }
};

onMounted(fetchPets);
</script>

<style scoped>
.pm-th-sort {
  cursor: pointer;
  user-select: none;
}
.pm-th-sort:hover i { opacity: 0.9; }
.pm-dot {
  display: inline-block;
  width: .6rem; height: .6rem;
  border-radius: 999px;
  margin-right: .4rem;
  vertical-align: middle;
}
.pm-chip {
  display: inline-block;
  padding: .15rem .4rem;
  border-radius: .5rem;
  background: #f1f5f9;
}
</style>