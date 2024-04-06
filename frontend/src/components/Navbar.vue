<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user.js'
import { computed, onMounted, onUnmounted } from "vue";

const userStore = useUserStore();
const router = useRouter();

const statusText = computed(() => {
  if (userStore.username) {
    return "Logout"
  }
  return "Login"
})

const handleLogout = () => {
  if (localStorage.getItem('token')) {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    userStore.logout();
  }
}

onMounted(() => {
  const token = localStorage.getItem('token');
  const username = localStorage.getItem('username');
  if (token) {
    userStore.setUsername(username);
    userStore.setLoggedInStatus('Logout');
  } else {
    userStore.setLoggedInStatus('Login');
  }
});

onUnmounted(() => {
  window.removeEventListener('storage', () => {
    const token = localStorage.getItem('token');
    const username = userStore.username;

    if (username) {
      localStorage.setItem('username', username);
    }

    if (token) {
      userStore.setLoggedInStatus('Logout');
    } else {
      userStore.setLoggedInStatus('Login');
    }
  });
});
</script>
<template>
    <header class="navbar">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/quiz">Play quiz</RouterLink>
          <RouterLink to="/about">About</RouterLink>
          <RouterLink to="/createEdit">Your quizes</RouterLink>
          <RouterLink to="/quizAttempts">Your progress</RouterLink>
          <RouterLink class="login" to="/login" @click="handleLogout">{{ statusText }}</RouterLink>
          <RouterLink v-if="userStore.username" to="/profile">My Profile</RouterLink>
        </nav>
    </header>
</template>
<style scoped>
.navbar {
  position: fixed;
  width: 100%;
  height: 40px;
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  align-items: center;
  background: var(--bg-white);
  box-shadow: 0 2px 4px rgba(0, 0, 255, .05);
}

nav {
  grid-column: 2 / -2;
  display: flex;
  gap: 1em;
}

.login {
  margin-left: auto;
}

a {
  color: var(--text-color);
  text-decoration: none;
  font-size: 0.8rem;
  line-height: 1.4rem;
}

a:hover,
a:active,
a.active {
  background: none;
  border-bottom: 0.1em solid var(--bg-light-blue);
  cursor: pointer;
}

@media (min-width: 720px) {
  .navbar {
    height: 60px;
  }

  a {
    font-size: 1rem;
  }
}

@media (min-width: 1480px) {
  .navbar {
    height: 80px;
    grid-template-columns: 1fr 1280px 1fr;
  }

  nav {
    grid-column: 3 / -3;
  }

  a {
    font-size: 1.125rem;
    line-height: 1.5em;
  }

  a:hover,
  a:active,
  a.active {
    border-bottom: 0.12em solid var(--bg-light-blue);
  }
}
</style>

