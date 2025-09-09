import axios from 'axios';
import { useAuthStore } from '../stores/auth';
import router from '../router';

// Base URL is same-origin because nginx proxies /api to the backend container
const http = axios.create({
  baseURL: '/api',
});

http.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('token'); 
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

http.interceptors.response.use(
  (res) => res,
  (err) => {
    const status = err?.response?.status;
    if (status === 401 || status === 403) {
      const url = err?.config?.url || '';
      if (url.startsWith('/auth/')) {
        return Promise.reject(err);
      }
      try {
        const auth = useAuthStore();
        auth.logout();
      } catch {}
      const current = router.currentRoute.value;
      if (current.name !== 'login') {
        router.push({
          name: 'login',
          query: { redirect: current.fullPath },
        }).catch(() => {});
      }
    }
    return Promise.reject(err);
  }
);

export default http;