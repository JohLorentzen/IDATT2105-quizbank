<script setup> 
import Login from '../components/Login.vue';
import ForgotPassword from '../components/ForgotPassword.vue';

import { ref } from 'vue';

const showLogin = ref(true);

function toLogin() {
  showForgotPassword.value = false;
  showLogin.value = true;
}

</script>

<template>
  <main>
    <Login v-if="showLogin"/>
  </main>
</template>

<style scoped>
main {
  background: var(--bg-light-gray);
}
</style>