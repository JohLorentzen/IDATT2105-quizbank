<script setup> 
import Login from '../components/Login.vue';
import ForgotPassword from '../components/ForgotPassword.vue';
import NewUser from '../components/NewUser.vue';

import { ref } from 'vue';

const showLogin = ref(true);
const showForgotPassword = ref(false);
const showNewUser = ref(false);

function resetAllToFalse() {
  showLogin.value = false;
  showForgotPassword.value = false;
  showNewUser.value = false;
}

function toLogin() {
  resetAllToFalse();
  showLogin.value = true;
}

function toUserCreation() {
  resetAllToFalse();
  showNewUser.value = true;
}

function toForgotPassword() {
  resetAllToFalse();
  showForgotPassword.value = true;
}
</script>

<template>
  <main>
    <Login v-if="showLogin" @show-new-user="toUserCreation" @show-forgot-password="toForgotPassword"/>
    <ForgotPassword v-if="showForgotPassword"  />
    <NewUser v-if="showNewUser" @to-login="toLogin"/>
  </main>
</template>

<style scoped>
main {
  margin-top: 100px;
  padding: 0;
}
</style>