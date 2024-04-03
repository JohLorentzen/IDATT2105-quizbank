<script setup>
import ProfileNavBar from "@/components/profile/ProfileNavBar.vue";
import {useUserStore} from "@/stores/user.js";
import {computed, ref} from "vue";

const userStore = useUserStore();
const username = ref(userStore.username);
const password = ref('');

const invalidFields = computed(() => {
  if (username.value.trim() === '') {
    return true;
  } else if (username.value === userStore.username) {
    return true;
  } else if (password.value.length > 0 && password.value.trim() === '') {
    return true;
  }
  return false;
});

function saveChanges() {
  userStore.username = username.value;
  console.log("Saved changes...");
}
</script>

<template>
  <ProfileNavBar />
  <h1>Edit Profile</h1>
  <form @submit.prevent="saveChanges">
    <div class="input-container">
      <label for="username">Username</label>
      <input type="text" id="username" v-model="username" autocomplete="off" />
    </div>
    <div class="input-container">
      <label for="password">New password (leave blank to keep old password)</label>
      <input type="password" id="password" v-model="password" />
    </div>
    <button :disabled="invalidFields" class="submit-btn" type="submit">Save</button>
  </form>
</template>

<style scoped>
h1, form, div, label, input, button {
  margin: 0;
  padding: 0;
}

.input-container {
  display: flex;
  flex-direction: column;
}

.input-container input {
  padding: 0.4em 0.4em;
  font-size: 1rem;
}

.submit-btn {
  display: block;
  width: 30%;
  margin: 1em auto;
  padding: 0.4em 0.8em;
}


</style>
