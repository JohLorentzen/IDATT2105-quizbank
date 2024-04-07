<script setup>

const props = defineProps({
  imageUrl: String,
});
</script>

<template>
  <div class="hero-image" :style="{ backgroundImage: 'url(' + imageUrl + ')' }">
    <div class="hero-text">
      <h1>Quizbank</h1>
      <p>A better way to create, share and learn!!</p>
    </div>
  </div>
</template>

<style scoped>
.hero-image {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 400px; 

}

.hero-text {
  text-align: center;
  color: white;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 10px;
  border-radius: 10px; /* Add rounded edges */
}
    

  </style>
  