<script setup>
import {RouterView, RouterLink, useRouter} from 'vue-router'
  import { useUserStore } from '@/stores/user.js'
  import {computed} from "vue";

  const userStore = useUserStore();
  const router = useRouter();

  const loggedInStatus = computed(() => {
    if (userStore.username) {
      return "Logout";
    }
    return "Login";
  })

  const handleLogout = () => {
    if (userStore.username) {
      localStorage.removeItem('token');
      userStore.logout();
    }
  }

</script>
<template>
    <header class="navbar">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/quiz">Play quiz</RouterLink>
          <RouterLink to="/about">About</RouterLink>
          <RouterLink to="/createEdit">Your quizes</RouterLink>
          <RouterLink class="login" to="/login" @click="handleLogout">{{ loggedInStatus }}</RouterLink>
        </nav>
    </header>
</template>
<style scoped>
.navbar {
  position: fixed;
  width: 100%;
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  align-items: center;
  background: var(--bg-white-soft);
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
  padding: 0.5em 0;
  font-size: 0.8rem;
}

a:hover,
a:active,
a.active {
  background: none;
  padding-bottom: 0.4em;
  border-bottom: 0.1em solid var(--bg-light-blue);
  cursor: pointer;
}
</style>

