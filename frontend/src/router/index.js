import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import Login from '../pages/Login.vue';
import PetsList from '../pages/PetList.vue';
import PetForm from '../pages/PetForm.vue';


const routes = [
    { path: '/login', name: 'login', component: Login },
    { path: '/', redirect: '/pets' },
    { path: '/pets', name: 'pets', component: PetsList, meta: { requiresAuth: true } },
    { path: '/pets/new', name: 'pet-new', component: PetForm, meta: { requiresAuth: true } },
    { path: '/pets/:id/edit', name: 'pet-edit', component: PetForm, meta: { requiresAuth: true } },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Simple auth guard
router.beforeEach((to) => {
    const auth = useAuthStore();
    if (to.meta.requiresAuth && !auth.token) {
        return { name: 'login' };
    }
});

export default router;