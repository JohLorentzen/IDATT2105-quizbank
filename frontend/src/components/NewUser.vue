<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/user.js';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();
const username = ref('');
const password = ref('');

const newUser = async (event) => {
    event.preventDefault();
    try {
        const response = await axios.post('http://localhost:8080/user/register', {
            username: username.value,
            password: password.value
        });
        if (response.status === 200) {
            localStorage.setItem('token', response.data);
            userStore.setUsername(username.value);
            router.push('/quiz'); 
        }
        console.log(userStore.getToken); 
    } catch (error) {
        // Handle error (e.g., display an error message)
        console.error('Registration failed:', error);
    }
};

</script>

<template>
    <div>
        <h1>New User</h1>
        <form @submit="newUser">
            <label for="username">Username</label>
            <input type="text" id="username" v-model="username" />
            <label for="password">Password</label>
            <input type="password" id="password" v-model="password" />
            <button type="submit">Create User</button>
        </form>
    </div>
</template>
