<script setup>
import { RouterView } from 'vue-router'
import Navbar from './components/Navbar.vue';
import Footer from './components/Footer.vue';

</script>

<template>
  <Navbar />
  <RouterView />
  <Footer />
</template>

<style>
.navbar {
  z-index: 10;
}

main {
  margin-top: 40px;
  min-height: 90vh;
}

@media (min-width: 720px) {
  main {
    margin-top: 60px;
  }
}

@media (min-width: 1480px) {
  main {
    margin-top: 80px;
  }
}
</style>
