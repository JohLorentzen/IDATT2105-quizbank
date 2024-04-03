<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/user.js';
import {computed, ref, watch} from 'vue';
import { useRouter } from 'vue-router';
import endpoints from "@/endpoints.json";

const userStore = useUserStore();
const router = useRouter();
const username = ref('');
const password = ref('');
const usernameExists = ref(false);


const emit = defineEmits(['toLogin']);

watch(username, () => {
  usernameExists.value = false;
})

const usernameTaken = computed(() => {
  if (usernameExists.value) {
    return {"red-underline": true};
  }
})


function newUser() {
  const url = `${endpoints.BASE_URL}${endpoints.CREATE_USER}`
  const userData = {
    username: username.value,
    password: password.value
  }
  const acceptedStatusCodes = {
    validateStatus: function(status) {
      return status >= 200 && status < 300 || 409;
    }
  }

  axios.post(url, userData, acceptedStatusCodes).then(response => {
    if (response.status === 201) {
      localStorage.setItem('token', response.data);
      userStore.setUsername(username.value);
      router.push('/quiz');
    } else if (response.status === 409) {
      // TODO: add feedback to UI
      usernameExists.value = true;
      console.log("Failed to create a user because username already exists")
    }
  }).catch(error => {
    console.error('Registration failed:', error);
  });
}

</script>

<template>
  <form @submit.prevent="newUser">
    <h1>Create Account</h1>
    <div class="input-container">
      <label for="username">Username</label>
      <input :class="usernameTaken" type="text" id="username" v-model="username" autocomplete="off" placeholder="Type your username"/>
      <p v-if="usernameExists" class="red">Username already exists</p>
    </div>
    <div class="input-container">
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" placeholder="Type your password" />
    </div>
    <button class="login-btn" type="submit">CREATE ACCOUNT</button>
    <div class="create-user-container">
      <p>Already have an account? <a @click="emit('toLogin')">Go to login</a></p>
    </div>
  </form>
</template>

<style scoped>

h1 {
  margin: 0 0 1em;
  padding: 0;
  font-weight: bold;
  text-align: center;
}

form {
  padding: 2em 2em 0;
  border-radius: 1em;
  width: 350px;
}

a {
  padding: 0;
  margin: 0;
}

a:hover {
  background: none;
  text-decoration: underline;
  cursor: pointer;
  color: #181818;
}

.input-container {
  display: flex;
  flex-direction: column;
  margin: 1.5em auto;
  width: 100%;
}

.input-container label, input {
  margin: 0;
}

.input-container label {
  font-size: 0.75rem;
}

.input-container input {
  padding: 0.8em 0.4em;
  font-size: 0.9rem;
  border: none;
  border-bottom: 2px solid #dddddd;
}

.input-container input:focus {
  outline: none;
  border-bottom: 2px solid rosybrown;
}

.red {
  color: red;
}

.input-container .red-underline {
  border-bottom: 2px solid red;
}

.input-container .red-underline:focus {
  border-bottom: 2px solid red;
}

.input-container p {
  margin: 0.4em 0 0;
  align-self: start;
  font-size: 0.7rem;
}

.login-btn {
  background: #eeeeee;
  display: block;
  margin: 2em auto 0;
  padding: 1.2em;
  width: 100%;
  font-weight: bold;
  font-size: 0.85rem;
  border: none;
  border-radius: 2em;
  cursor: pointer;
}

.login-btn:hover {
  background: #dddddd;
}

.create-user-container {
  margin: 3em 0 0;
  padding: 0;
  font-size: 0.8rem;
  text-align: center;
}

.create-user-container a {
  border-bottom: 1px solid #777777;
  padding: 0 0 0.09em;
}

.create-user-container a:hover {
  text-decoration: none;
  color: #181818;
  font-weight: bolder;
}
</style>
