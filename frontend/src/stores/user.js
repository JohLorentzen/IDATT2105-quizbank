import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        username: '',
    }),
    getters: {
        getUsername() {
            return this.username;
        },
    },  
    actions: {
        setUsername(username) {
            this.username = username;
        },
        logout() {
            this.username = '';
        } 
    },
});