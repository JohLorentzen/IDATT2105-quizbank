<script setup>
import { RouterView } from 'vue-router'
import Navbar from './components/Navbar.vue';
import Footer from './components/Footer.vue';

</script>

<template>
  <Navbar class="navbar" />
  <RouterView class="main-view"/>
  <Footer class="footer"/>
</template>

<style scoped>
.navbar {
  z-index: 10;
}

.main-view {
  margin-top: 40px;
}

@media (min-width: 720px) {
  .main-view {
    margin-top: 60px;
  }
}

@media (min-width: 1480px) {
  .main-view {
    margin-top: 80px;
  }
}
</style>
