<script setup>
  import { RouterView, RouterLink } from 'vue-router'
</script>
<template>
    <div class="navbar">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/about">About</RouterLink>
      </nav>
      <nav class="login">
          <RouterLink to="/login">Login</RouterLink>
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
