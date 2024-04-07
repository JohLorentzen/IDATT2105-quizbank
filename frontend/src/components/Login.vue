<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/user.js';
import {computed, ref, watch} from 'vue';
import { useRouter } from 'vue-router';
import endpoints from '@/endpoints.json'

const userStore = useUserStore();
const router = useRouter();
const username = ref('');
const password = ref('');
const createNewUser = ref(false);
const usernameExists = ref(false);
const userNotFound = ref(false);

const emit = defineEmits(['showNewUser', 'showForgotPassword'])

const currentEvent = computed(() => {
  if (createNewUser.value) {
    return "Create User";
  }
  return "Login";
})

const errorStylingUsername = computed(() => {
  if (usernameExists.value || userNotFound.value) {
    return {"red-underline": true};
  }
})

const errorStylingPassword = computed(() => {
  if (userNotFound.value) {
    return {"red-underline": true};
  }
})

watch(username, () => {
  userNotFound.value = false;
  usernameExists.value = false;
})

watch(password, () => {
  userNotFound.value = false;
})


function toggleCreateNewUser() {
  userNotFound.value = false;
  usernameExists.value = false;
  createNewUser.value = !createNewUser.value;
}

function handleLoginOrCreateUser() {
  if (createNewUser.value) {
    newUser();
  } else {
    login();
  }
}

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
      localStorage.setItem('username', username.value);
      userStore.setUsername(username.value);
      router.push('/quiz');
    } else if (response.status === 409) {
      usernameExists.value = true;
    }
  }).catch(error => {
    console.error('Registration failed:', error);
  });
}


function login() {
  const url = `${endpoints.BASE_URL}${endpoints.LOGIN}`
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
      userNotFound.value = false;
      localStorage.setItem('token', response.data);
      localStorage.setItem('username', username.value);
      localStorage.setItem('tokentime', Date.now());
      userStore.setUsername(username.value);
      userStore.setLoggedInStatus('Logout')
      router.push('/quiz');
    } else if (response.status === 401) {
      userNotFound.value = true;
    }
  }).catch(error => {
    console.log("Error: " + error.response.status);
  })
};

// TODO: remove red underscores once user is typing
</script>

<template>
  <form @submit.prevent="handleLoginOrCreateUser">
    <h1>{{ currentEvent }}</h1>
    <div v-if="userNotFound" class="error-container">
      <p>Sorry, we can't find an account with that username. Please verify that the username and password is correct and try again.</p>
    </div>
    <div class="input-container">
      <label for="username">Username</label>
      <input type="text" id="username" v-model="username" autocomplete="off" placeholder="Type your username" :class="errorStylingUsername"/>
      <p v-if="usernameExists" class="red">Username already exists</p>
    </div>
    <div class="input-container">
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" placeholder="Type your password" :class="errorStylingPassword"/>
    </div>
    <button :disabled="userNotFound || usernameExists" class="login-btn" type="submit">{{ currentEvent }}</button>
    <div class="create-user-container">
      <p v-if="!createNewUser">New here? <a @click="toggleCreateNewUser">Create an account</a></p>
      <p v-else>Already have an account? <a @click="toggleCreateNewUser">Go to login</a></p>
    </div>
  </form>
</template>

<style scoped>

h1 {
  margin: 0 0 1em;
  font-weight: bold;
  text-align: center;
}

form {
  margin: 4em auto;
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

.error-container {
  margin: 0;
  padding: 0.8em;
  background: #ffa69e;
  border-radius: 3px;
}

.error-container p {
  margin: 0;
  padding: 0;
  font-size: 0.7rem;
}


.input-container {
  display: flex;
  flex-direction: column;
  margin: 1.5em auto;
  width: 100%;
  gap: 0.3em;
}

.input-container label, input {
  margin: 0;
}

.input-container label {
  color: #777777;
  margin-left: 0.4em;
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
  border-bottom: 2px solid var(--input-field-border);
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

.input-container a {
  margin: 0.4em 0 0;
  align-self: end;
  font-size: 0.7rem;
}

.input-container p {
  margin: 0.4em 0 0;
  align-self: start;
  font-size: 0.7rem;
}

.login-btn {
  background: var(--button-bg-strong-blue);
  color: var(--text-color-white);
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
  background: var(--button-bg-hover-blue);
}

.login-btn:active {
  background: var(--button-bg-active-blue);
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

@media (min-width: 720px) {
  .login-btn {
    font-size: 1rem;
  }
}
</style>
