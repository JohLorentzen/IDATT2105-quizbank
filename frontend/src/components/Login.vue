<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/user.js';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();
const username = ref('');
const password = ref('');

const login = async (event) => {
  event.preventDefault();
  try {
    const response = await axios.post('http://localhost:8080/user/login', {
      username: username.value,
      password: password.value
    });
    if (response.status === 200) {
      userStore.setToken(response.data);
      userStore.setUsername(username.value);
      router.push('/quiz');
    } else {
      console.log('Login failed');
    }
    console.log(userStore.getToken);
  } catch (error) {
    console.error('Login failed:', error);
  }
};
</script>

<template>
  <main>
    <h1>Login</h1>
    <form @submit="login">
      <label for="username">Username</label>
      <input type="text" id="username" v-model="username" />
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" />
      <button type="submit">Login</button>
    </form>
  </main>
</template>
