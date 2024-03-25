<script setup>
  import { RouterView, RouterLink } from 'vue-router'
  import { useUserStore } from '@/stores/user.js'

  const userStore = useUserStore();

  const handleLogout = () => {
    userStore.logout();
  }
</script>
<template>
    <div class="navbar">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/quiz">Quiz</RouterLink>
          <RouterLink to="/about">About</RouterLink>
      </nav>
      <nav class="login">
          <template v-if="userStore.token">
            <RouterLink to="/" @click="handleLogout">Logout</RouterLink>
          </template>
          <template v-else>
            <RouterLink to="/login">Login</RouterLink>
          </template>
      </nav>
    </div>
</template>
<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #ebebeb;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.login {
  margin-left: auto;
}
</style>
