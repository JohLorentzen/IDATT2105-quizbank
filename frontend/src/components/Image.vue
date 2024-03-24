<script>
  import { defineComponent, ref } from 'vue';
  const src = defineprops('src');
</script>

<template>
    <img :src="src" 
         :alt="alt" 
         :class="customClass"
         @error="handleError"
         @load="handleLoad"
    />
  </template>
  
  <style scoped>
  img {
    display: block;
    max-width: 100%;
    height: auto;
  }
  </style>
  