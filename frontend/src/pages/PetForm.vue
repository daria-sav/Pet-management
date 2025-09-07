<template>
  <div class="card shadow-sm">
    <div class="card-body">
      <h4 class="mb-3">{{ isEdit ? 'Edit Pet' : 'Add Pet' }}</h4>

      <div v-if="loadingLookups" class="alert alert-info">Loading lookups…</div>
      <div v-if="loadError" class="alert alert-danger">{{ loadError }}</div>

      <form v-if="!loadingLookups" @submit.prevent="onSubmit" novalidate>
        <div class="row g-3">
          <div class="col-md-6">
            <label class="form-label">Name *</label>
            <input v-model.trim="form.name" class="form-control" required />
          </div>

          <div class="col-md-6">
            <label class="form-label d-flex align-items-center justify-content-between">
              <span>Identification Number *</span>
              <span v-if="idCheckPending" class="badge text-bg-secondary">checking…</span>
              <span v-else-if="idAvailable === true" class="badge text-bg-success">available</span>
              <span v-else-if="idAvailable === false" class="badge text-bg-danger">taken</span>
            </label>
            <input
              v-model.trim="form.identificationNumber"
              class="form-control"
              required
              :class="{'is-invalid': idAvailable === false}"
            />
            <div v-if="idAvailable === false" class="invalid-feedback">
              This identification number is already taken.
            </div>
          </div>

          <div class="col-md-6">
            <label class="form-label">Type *</label>
            <select v-model="form.typeId" class="form-select" required>
              <option :value="null" disabled>Select type</option>
              <option v-for="t in petTypes" :key="t.id" :value="t.id">{{ t.name }}</option>
            </select>
          </div>

          <div class="col-md-6">
            <label class="form-label">Fur Color *</label>
            <input v-model.trim="form.furColor" class="form-control" required />
          </div>

          <div class="col-md-6">
            <label class="form-label">Country (optional)</label>
            <select v-model="form.countryId" class="form-select">
              <option :value="null">—</option>
              <option v-for="c in countries" :key="c.id" :value="c.id">{{ c.name }}</option>
            </select>
          </div>
        </div>

        <div class="mt-3 d-flex gap-2">
          <button class="btn btn-primary" :disabled="saving">
            {{ saving ? 'Saving…' : (isEdit ? 'Update' : 'Create') }}
          </button>
          <router-link class="btn btn-outline-secondary" :to="{ name: 'pets' }">Cancel</router-link>
        </div>

        <div v-if="serverError" class="alert alert-danger mt-3">{{ serverError }}</div>
        <ul v-if="fieldErrors.length" class="alert alert-warning mt-3 mb-0">
          <li v-for="(msg, i) in fieldErrors" :key="i">{{ msg }}</li>
        </ul>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getPet, createPet, updatePet, checkIdentification } from '../api/pets';
import { getPetTypes, getCountries } from '../api/lookups';

const route = useRoute();
const router = useRouter();

const isEdit = computed(() => !!route.params.id);

const form = reactive({
  name: '',
  identificationNumber: '',
  typeId: null,
  furColor: '',
  countryId: null, // optional
});

const petTypes = ref([]);
const countries  = ref([]);
const loadingLookups = ref(true);
const loadError = ref('');
const saving = ref(false);
const serverError = ref('');
const fieldErrors = ref([]);

const idCheckPending = ref(false);
const idAvailable = ref(null); // null = didn't check, true/false = result
let idCheckTimer = null;

function parseFieldErrors(err) {
  // Works with default Spring validation error response or custom handler
  const res = err?.response;
  if (!res) return ['Request failed.'];
  
  if (Array.isArray(res.data?.errors)) {
    return res.data.errors.map(e => e.defaultMessage || e.message || JSON.stringify(e));
  }
  if (Array.isArray(res.data)) {
    return res.data.map(e => e.defaultMessage || e.message || JSON.stringify(e));
  }
  if (res.data?.message) {
    return [res.data.message];
  }
  return ['Validation failed or constraint violation.'];
}

const loadLookups = async () => {
  try {
    const [t, c] = await Promise.all([getPetTypes(), getCountries()]);
    petTypes.value = t.data;
    countries.value = c.data;
  } catch (e) {
    loadError.value = 'Failed to load lookups.';
  } finally {
    loadingLookups.value = false;
  }
};

const loadPetIfEdit = async () => {
  if (!isEdit.value) return;
  try {
    const { data } = await getPet(route.params.id);
    // Map API → form
    form.name = data.name;
    form.identificationNumber = data.identificationNumber;
    form.typeId = data.typeId;
    form.furColor = data.furColor;
    form.countryId = data.countryId ?? null;
  } catch (e) {
    loadError.value = 'Failed to load pet.';
  }
};

const onSubmit = async () => {
  serverError.value = '';
  fieldErrors.value = [];
  saving.value = true;
  try {
    const payload = {
      name: form.name,
      identificationNumber: form.identificationNumber,
      typeId: form.typeId,
      furColor: form.furColor,
      countryId: form.countryId,
    };
    if (isEdit.value) {
      await updatePet(route.params.id, payload);
    } else {
      await createPet(payload);
    }
    router.push({ name: 'pets' });
  } catch (e) {
    fieldErrors.value = parseFieldErrors(e);
    if (!fieldErrors.value.length) serverError.value = 'Save failed.';
  } finally {
    saving.value = false;
  }
};

function scheduleIdCheck() {
  clearTimeout(idCheckTimer);
  const val = form.identificationNumber?.trim();
  if (!val) {
    idAvailable.value = null;
    return;
  }
  idCheckTimer = setTimeout(async () => {
    try {
      idCheckPending.value = true;
      const res = await checkIdentification(val, isEdit.value ? route.params.id : undefined);
      idAvailable.value = !!res.data.available;
    } catch {
      idAvailable.value = null; 
    } finally {
      idCheckPending.value = false;
    }
  }, 400);
}

watch(() => form.identificationNumber, scheduleIdCheck);

onMounted(async () => {
  await loadLookups();
  await loadPetIfEdit();
});
</script>