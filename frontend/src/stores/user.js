import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        username: '',
        userId: '',
        loggedInStatus: 'Login',
    }),
    getters: {
        getUsername() {
            return this.username;
        },
        getUserId() {
            return this.userId;
        },
        getLoggedInStatus() {
            return this.loggedInStatus;
        }
    },  
    actions: {
        setUsername(username) {
            this.username = username;
        },
        setUserId(userId) {
            this.userId = userId;
        },
        setLoggedInStatus(status) {
            this.loggedInStatus = status;
        },
        logout() {
            this.username = '';
            this.userId = '';
        },
        logoutStatus() {
            this.loggedInStatus = 'Login';
        }
    },
});