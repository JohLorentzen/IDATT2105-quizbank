<script setup>
  import { RouterView, RouterLink } from 'vue-router'
  import { useUserStore } from '@/stores/user.js'

  const userStore = useUserStore();

  const handleLogout = () => {
    localStorage.removeItem('token');
    userStore.logout();
  }
</script>
<template>
    <div class="navbar">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/quiz">Play quiz</RouterLink>
          <RouterLink to="/about">About</RouterLink>
          <RouterLink to="/createEdit">Your quizes</RouterLink>
      </nav>
      <nav class="login">

            <RouterLink to="/" @click="handleLogout">Logout</RouterLink>
            <RouterLink to="/login">Login</RouterLink>
          
      </nav>
    </div>
</template>
<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  padding: 15px 10%;
  background-color: #f0f2f5;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid #d3d4d8;
  z-index: 1000;
}

.navbar nav a {
  color: #333;
  text-decoration: none;
  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.navbar nav a:hover,
.navbar nav a:focus {
  background-color: #e1e3e6;
}

.login {
  margin-left: auto;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .navbar {
    padding: 10px 5%; /* Smaller padding on smaller screens */
  }

  .navbar nav a {
    padding: 8px 10px; /* Adjust link padding on smaller screens */
  }
}
</style>

