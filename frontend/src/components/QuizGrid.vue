<script setup>
import { ref } from 'vue';
const props = defineProps(['quizes']);
const emit = defineEmits(['selectQuiz']);
const currentQuiz = ref(null);

function playQuiz(quiz) {
    currentQuiz.value = quiz;
    console.log(currentQuiz.value);
    emit('selectQuiz', currentQuiz.value);
}

</script>
<template>
    <div v-if="!currentQuiz" class="quiz-grid">
      <div class="quiz-card" v-for="quiz in quizes" :key="quiz.quizId" @click="playQuiz(quiz)">
        <h2>{{ quiz.quizName }}</h2>
        <p class="category">{{ quiz.category }}</p>
        <p class="questions">{{ quiz.questions ? `${quiz.questions.length} questions` : 'No questions' }}</p>
      </div>
    </div>
</template>
<style>
.quiz-grid {
  margin-top: 2em;
  grid-column: 2 / -2;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
  grid-auto-rows: 120px;
  gap: 2em;
}

.quiz-card {
  background: var(--bg-very-light-blue);
  padding: 1em;
  display: flex;
  flex-direction: column;
  align-items: start;
  border-radius: 0.8em;
  border: 1px solid var(--border-very-light-blue);
}

.quiz-grid button:hover,
.quiz-grid button:focus {
  background-color: #e9e9e9;
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.quiz-grid h2 {
  font-size: 1rem;
  font-weight: bold;
}

.category {
  margin-top: 0.8em;
  font-size: 0.46rem;
  padding: 0.5em 1em;
  border-radius: 1em;
  font-weight: bold;
  color: white;
  background-color: var(--bg-light-blue);
}

p.questions {
  margin-top: auto;
  font-size: 0.75rem;
  color: var(--text-color-grey);
}
</style>