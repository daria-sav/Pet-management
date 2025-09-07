import { defineStore } from 'pinia';
import http from '../api/http';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    username: localStorage.getItem('username') || null,
  }),
  actions: {
    async login(username, password) {
      const res = await http.post('/auth/login', { username, password });
      this.token = res.data.token;
      this.username = username;
      localStorage.setItem('token', this.token);
      localStorage.setItem('username', this.username);
    },
    logout() {
      this.token = null;
      this.username = null;
      localStorage.removeItem('token');
      localStorage.removeItem('username');
    },
  },
});
