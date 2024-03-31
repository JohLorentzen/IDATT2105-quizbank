<script setup> 
import Login from '../components/Login.vue';
import ForgotPassword from '../components/ForgotPassword.vue';
import NewUser from '../components/NewUser.vue';

import { ref } from 'vue';

const showLogin = ref(true);
const showForgotPassword = ref(false);
const showNewUser = ref(false);

const newUser = () => {
    showLogin.value = false;
    showForgotPassword.value = false;
    showNewUser.value = true;
};

const forgotPassword = () => {
    showLogin.value = false;
    showForgotPassword.value = true;
    showNewUser.value = false;
};

</script>

<template>
  <main>
    <Login v-if="showLogin" @show-new-user="newUser" @show-forgot-password="forgotPassword"/>
    <ForgotPassword v-if="showForgotPassword" />
    <NewUser v-if="showNewUser"/>
  </main>
</template>

<style scoped>
main {
  margin-top: 100px;
  padding: 0;
}
</style>