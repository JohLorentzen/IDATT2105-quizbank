<script setup>
const emit = defineEmits(['showStats', 'showEditProfile'])
</script>

<template>
  <div class="nav-container">
    <ul>
      <RouterLink to="/profile/stats" >Stats</RouterLink>
      <RouterLink to="/profile/edit" >Edit profile</RouterLink>
    </ul>
  </div>
</template>

<style scoped>
.nav-container {
  width: 100%;
}

.nav-container ul {
  margin: 0;
  padding: 0;
  list-style: none;
  display: flex;
}

a {
  color: #282828;
  text-decoration: none;
  padding-bottom: 2px;
}

a:hover,
a:active,
a.active {
  background: none;
  padding-bottom: 0;
  cursor: pointer;
  border-bottom: 2px solid #cccccc;
}
</style>
