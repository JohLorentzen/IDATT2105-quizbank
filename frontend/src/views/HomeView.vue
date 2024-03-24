<script setup>
import Heroimage from '../components/Heroimage.vue';
</script>

<template>
  <main>
    <h1>Home</h1>
    <Heroimage imageUrl="https://images.pexels.com/photos/3153198/pexels-photo-3153198.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" />
    <section>
      <h2>What is Quizbank?</h2>
      <p>Quizbank is a platform that allows you to create, share and learn from quizzes. It is a fun and interactive way to learn new things and test your knowledge.</p>
    </section>
    <button @click="$router.push('/quiz')">Start Quiz</button>
  </main>
</template>
