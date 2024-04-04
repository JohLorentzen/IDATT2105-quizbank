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
  grid-column: 1 / -1;
}

.main-view {
  margin-top: 40px;
  grid-column: 2 / -2;
}

.footer {
  grid-column: 2 / -2;
}
</style>
