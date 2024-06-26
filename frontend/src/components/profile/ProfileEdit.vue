<script setup>
import ProfileNavBar from "@/components/profile/ProfileNavBar.vue";
import {useUserStore} from "@/stores/user.js";
import {computed, ref} from "vue";
import endpoints from '@/endpoints.json'
import axios from 'axios';

const userStore = useUserStore();
const username = ref(userStore.username);
const password = ref('');
const buttonText = ref('Save');
const updateStatus = ref('')

const invalidFields = computed(() => {
  if (username.value.trim() === '') {
    return true;
  } else if (username.value === userStore.username && password.value.length === 0) {
    return true;
  } else if (password.value.length > 0 && password.value.trim() === '') {
    return true;
  }
  return false;
});

const messageColor = computed(() => {
  return {
    'successful': updateStatus.value === 'UPDATE_SUCCESSFUL',
    'unsuccessful': updateStatus.value !== 'UPDATE_SUCCESSFUL'
  }
})

const messageToUser = computed(() => {
  switch (updateStatus.value) {
    case 'UPDATE_SUCCESSFUL':
      return 'Account was successfully updated.'
    case 'AUTHENTICATION_FAILED':
      return 'Authentication failed.. Please try again.'
    case 'USERNAME_TAKEN':
      return 'Username is already taken.. Please try again.'
    case 'ERROR':
      return 'Error.. Please try again.'
    default:
      return 'Something unexpected happened. Please try again.'
  }
})

function saveChanges() {
  buttonText.value = 'Updating..'

  const url = `${endpoints.BASE_URL}${endpoints.UPDATE_USER_PROFILE}`
  const newUserInfo = {
    "username": username.value,
    "password": password.value,
  }
  const config = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
    validateStatus: function (status) {
      return status >= 200 && status < 300 || status === 409;
    }
  };

  axios.put(url, newUserInfo, config).then(response => {
    console.log("Got a response..")
    if (response.status === 201) {
      userStore.setUsername(username.value);
      localStorage.setItem('token', response.data);
      updateStatus.value = 'UPDATE_SUCCESSFUL';
      // show feedback that user was updated successfully
    } else if (response.status === 403) {
      // show feedback that authentication failed
      updateStatus.value = 'AUTHENTICATION_FAILED';
    } else if (response.status === 409) {
      // show feedback that username exists and cannot be overwritten
      updateStatus.value = 'USERNAME_TAKEN';
    }
  }).catch(error => {
    updateStatus.value = 'ERROR'
    console.log(error);
  });

  buttonText.value = 'Save'
}
</script>

<template>
  <main>
    <form @submit.prevent="saveChanges">
      <div class="feedback-container" :class="messageColor" v-if="updateStatus !== ''">
        <p>{{ messageToUser }}</p>
      </div>
      <h1>Edit Profile</h1>
      <div class="input-container">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="username" autocomplete="off" />
      </div>
      <div class="input-container">
        <label for="password">New password (leave blank to keep old password)</label>
        <input type="password" id="password" v-model="password" />
      </div>
      <button :disabled="invalidFields" class="submit-btn" type="submit">{{buttonText}}</button>
    </form>
  </main>
</template>

<style scoped>
h1, form, div, label, input, button, p {
  margin: 0;
  padding: 0;
}

main {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  background: var(--bg-very-light-blue);
}

form {
  grid-column: 2 / -2;
  background: var(--bg-light-gray);
  display: flex;
  flex-direction: column;
  justify-self: center;
  align-self: center;
  padding: 2em;
  border-radius: 0.5em;
  gap: 1em;
}

h1 {
  font-weight: bold;
  font-size: 2.6rem;
}

button {
  font-weight: bold;
  border: none;
  cursor: pointer;
  padding: 1.4em 2.8em;
  border-radius: 0.6em;
  background-color: #0056b3;
  color: white;
}

button:hover {
  background-color: #004494;
}

button:disabled {
  background-color: var(--bg-light-gray-shadow);
  color: var(--text-color-grey);
  cursor: default;
}

.feedback-container {
  padding: 0.8em 1.2em;
  border-radius: 3px;
}


.successful {
  background: #c1ff9b;
}

.unsuccessful {
  background: #ffa69e;
}

.input-container {
  display: flex;
  flex-direction: column;
}

.input-container input {
  padding: 0.4em 0.4em;
  font-size: 1rem;
}



</style>
