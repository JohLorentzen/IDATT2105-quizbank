<script setup> 
import Login from '../components/Login.vue';
import ForgotPassword from '../components/ForgotPassword.vue';

import { ref } from 'vue';

const showLogin = ref(true);
const showForgotPassword = ref(false);

function toLogin() {
  showForgotPassword.value = false;
  showLogin.value = true;
}

function toForgotPassword() {
  showLogin.value = true;
  showForgotPassword.value = true;
}
</script>

<template>
  <main>
    <Login v-if="showLogin" @show-forgot-password="toForgotPassword"/>
    <ForgotPassword v-if="showForgotPassword"  />
  </main>
</template>

<style scoped>
main {
  background: var(--bg-light-gray);
}
</style>