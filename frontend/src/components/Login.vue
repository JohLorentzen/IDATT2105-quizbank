<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/user.js';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import endpoints from '@/endpoints.json'

const userStore = useUserStore();
const router = useRouter();
const username = ref('');
const password = ref('');

const login = async () => {
  const url = `${endpoints.HOST_URL}${endpoints.LOGIN}`
  const userData = {
    username: username.value,
    password: password.value
  }
  const acceptedStatusCodes = {
    validateStatus: function(status) {
      return status >= 200 && status < 300 || 401;
    }
  }

  axios.post(url, userData, acceptedStatusCodes).then(response => {
    if (response.status === 200) {
      // display successful login
      localStorage.setItem('token', response.data);
      userStore.setUsername(username.value);
      router.push('/quiz');
    } else if (response.status === 401) {
      // display unsuccessful login
      console.log("User not found...")
    }
  }).catch(error => {
    console.log("Error: " + error.response.status);
  })
};
</script>

<template>
  <main>
    <h1>Login</h1>
    <form @submit.prevent="login">
      <label for="username">Username</label>
      <input type="text" id="username" v-model="username" />
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" />
      <button type="submit">Login</button>
    </form>
  </main>
</template>
