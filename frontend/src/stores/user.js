import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        token: '',
        username: '',
    }),
    getters: {
        getToken() {
            return this.token;
        },
        getUsername() {
            return this.username;
        },
    },
    actions: {
        setToken(token) {
            this.token = token;
        },
        setUsername(username) {
            this.username = username;
        },
        logout() {
            this.token = '';
            this.username = '';
        } 
    },
});