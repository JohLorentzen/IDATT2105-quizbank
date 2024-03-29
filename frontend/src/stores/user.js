import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        username: '',
        userId: '',
    }),
    getters: {
        getUsername() {
            return this.username;
        },
        getUserId() {
            return this.userId;
        },
    },  
    actions: {
        setUsername(username) {
            this.username = username;
        },
        setUserId(userId) {
            this.userId = userId;
        },
        logout() {
            this.username = '';
            this.userId = '';
        } 
    },
});