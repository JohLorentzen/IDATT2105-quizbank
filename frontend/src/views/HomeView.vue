<script setup>
import Heroimage from '../components/Heroimage.vue';
</script>

<template>
  <main class="background-image" alt="A women gaining knowlegde by reading a book">
    <section class="info-section">
      <h1>What is Quizbank?</h1>
      <p>Quizbank is a platform that allows you to create, share and learn from quizzes. It is a fun and interactive way to learn new things and test your knowledge.</p>
      <button @click="$router.push('/quiz')">Play now</button>
    </section>
  </main>
</template>

<style scoped>
main {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
}

.blue {
  color: var(--bg-very-light-blue)
}

.background-image {
  position: relative;
  height: 90vh;
}

.background-image::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url("https://images.unsplash.com/photo-1534612368275-e4cfd0f21b2d?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
  background-size: cover;
  background-position: center;
  filter: brightness(50%) grayscale(100%);
}

.info-section {
  position: relative;
  grid-column: 2 / -2;
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
}

h1 {
  font-weight: bold;
  color: var(--text-color-white);
  font-size: 3.4rem;
}

p {
  font-size: 1.1rem;
  max-width: 500px;
  margin-top: 1em;
  color: var(--text-color-white);
  font-weight: bolder;
  line-height: 1.5em;
}

button {
  margin-top: 2em;
  border: none;
  padding: 1.3em 2.2em;
  border-radius: 1em;
  background: var(--button-bg-strong-blue);
  font-weight: bold;
  color: white;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background: var(--button-bg-hover-blue);
}

button:active {
  background: var(--button-bg-active-blue);
}

@media (min-width: 720px) {
  h1 {
    font-size: 3.6rem;
  }

  p {
    font-size: 1.2rem;
  }
}

@media (min-width: 1480px) {
  main {
    grid-template-columns: 1fr 1280px 1fr;
  }

  h1 {
    font-size: 4rem;
  }

  p {
    max-width: 700px;
    font-size: 1.4rem;
  }
}

</style>