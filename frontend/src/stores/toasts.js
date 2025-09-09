import { defineStore } from 'pinia';

let idSeq = 1;

export const useToasts = defineStore('toasts', {
  state: () => ({ items: [] }),
  actions: {
    show({ title, message = '', variant = 'success', timeout = 3000 }) {
      const id = idSeq++;
      this.items.push({ id, title, message, variant });
      if (timeout) setTimeout(() => this.dismiss(id), timeout);
    },
    success(message, title = 'Success') { this.show({ title, message, variant: 'success' }); },
    error(message, title = 'Error') { this.show({ title, message, variant: 'danger', timeout: 5000 }); },
    info(message, title = 'Info') { this.show({ title, message, variant: 'info' }); },
    dismiss(id) { this.items = this.items.filter(t => t.id !== id); },
    clear() { this.items = []; }
  }
});
