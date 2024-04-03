<script setup>
import ProfileNavBar from "@/components/profile/ProfileNavBar.vue";

function showStats() {
  console.log("Show stats applied")
}

function showEditProfile() {
  console.log("Show edit profile applied")
}
</script>

<template>
  <ProfileNavBar />
  <h1>Profile View</h1>
</template>

<style scoped>

</style>