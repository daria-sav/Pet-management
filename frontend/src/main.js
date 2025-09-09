import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './style.css'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css';

import { useAuthStore } from './stores/auth' 

const app = createApp(App);

const pinia = createPinia();
app.use(pinia);
app.use(router);

// === Restore auth state from sessionStorage ===
const auth = useAuthStore();
auth.restoreFromStorage();

app.mount('#app');