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
    <h1>Welcome in</h1>
    <div v-if="showLogin" >
        <Login />
    </div>
    <div v-if="showForgotPassword">
        <ForgotPassword />
    </div>
    <div v-if="showNewUser">
        <NewUser />
    </div>   
    <div>
        <button @click="newUser">New user?</button>
        <button @click="forgotPassword">Forgot your password?</button>
    </div>
</template>