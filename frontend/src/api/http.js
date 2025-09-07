import axios from 'axios';

// Base URL is same-origin because nginx proxies /api to the backend container
const http = axios.create({
  baseURL: '/api',
});

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default http;