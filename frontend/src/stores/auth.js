import { defineStore } from 'pinia';
import http from '../api/http';

function getStorage() {
  return sessionStorage; 
}

function parseJwt(token) {
  try {
    const payload = token.split('.')[1];
    const json = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
    return JSON.parse(json);
  } catch {
    return null;
  }
}

let logoutTimer = null;

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: getStorage().getItem('token') || null,
    username: getStorage().getItem('username') || null,
  }),
  getters: {
    isAuthenticated: (s) => !!s.token,
  },
  actions: {
    _scheduleAutoLogoutFromToken() {
      clearTimeout(logoutTimer);
      if (!this.token) return;

      const payload = parseJwt(this.token);
      if (!payload?.exp) return; 

      const msLeft = payload.exp * 1000 - Date.now();
      if (msLeft <= 0) {
        this.logout();
        return;
      }
      logoutTimer = setTimeout(() => this.logout(), msLeft);
    },

    async login(username, password) {
      const res = await http.post('/auth/login', { username, password });
      this.token = res.data.token;
      this.username = username;

      const storage = getStorage();
      storage.setItem('token', this.token);
      storage.setItem('username', this.username);

      this._scheduleAutoLogoutFromToken();
    },

    restoreFromStorage() {
      const storage = getStorage();
      this.token = storage.getItem('token');
      this.username = storage.getItem('username');
      this._scheduleAutoLogoutFromToken();
    },

    logout() {
      clearTimeout(logoutTimer);
      this.token = null;
      this.username = null;
      const storage = getStorage();
      storage.removeItem('token');
      storage.removeItem('username');
    },
  },
});