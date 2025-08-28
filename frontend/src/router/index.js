import { createRouter, createWebHistory } from 'vue-router'
import Login from '../pages/Login.vue'


const routes = [
    { path: '/login', name: 'login', component: Login },
    { path: '/', redirect: '/login' },
]


export default createRouter({
    history: createWebHistory(),
    routes,
})