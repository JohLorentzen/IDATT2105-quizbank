<script setup>
import {computed, ref} from 'vue';
import ShareModal from './ShareModal.vue';

const props = defineProps(['quizzes']);
const emit = defineEmits(['selectQuiz']);
const currentQuiz = ref(null);
const showModal = ref(false);

const availableQuizzesTitle = computed(() => {
  if (props.quizzes.length > 0) {
    return "Available quizzes"
  }
  return "No available quizzes"
});

function playQuiz(quiz) {
  currentQuiz.value = quiz;
  console.log(currentQuiz.value);
  emit('selectQuiz', currentQuiz.value);
}

function shareQuiz(quiz) {
  currentQuiz.value = quiz;
  showModal.value = true;
}
</script>

<template>
  <h1>{{ availableQuizzesTitle }}</h1>
  <div v-if="!currentQuiz" class="quiz-grid">
    <div class="quiz-card" v-for="quiz in quizzes" :key="quiz.quizId" @click="playQuiz(quiz)">
      <h2>{{ quiz.quizName }}</h2>
      <p class="category">{{ quiz.category }}</p>
      <p class="questions">{{ quiz.questions ? `${quiz.questions.length} questions` : 'No questions' }}</p>
      <button @click.stop="shareQuiz(quiz)" class="share-button">Share</button>
    </div>
  </div>
  <ShareModal v-if="showModal" :quiz="currentQuiz" @close="showModal = false" />
</template>


<style scoped>
h1 {
  color: var(--text-color-grey);
  font-size: 0.8rem;
  letter-spacing: 0.04em;
  font-weight: bolder;
  grid-column: 2 / -2;
  padding-left: 1em;
  align-self: center;
}

.quiz-grid {
  margin-top: 1em;
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

.quiz-card:hover,
.quiz-card:focus {
  cursor: pointer;
  background-color: var(--bg-very-light-blue-hover);
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.quiz-grid h2 {
  font-size: 1.1rem;
  font-weight: bold;
}

.category {
  margin-top: 0.8em;
  font-size: 0.54rem;
  padding: 0.5em 1em;
  border-radius: 1em;
  font-weight: bold;
  color: white;
  background-color: var(--bg-light-blue);
}

p.questions {
  margin-top: auto;
  font-size: 0.85rem;
  color: var(--text-color-grey);
}

@media (min-width: 720px) {
  h1 {
    font-size: 1.1rem;
  }

  .quiz-grid {
    grid-auto-rows: 150px;
  }

  .quiz-grid h2 {
    font-size: 1.4rem;
  }

  .category {
    font-size: 0.7rem;
  }

  p.questions {
    font-size: 1rem;
  }
}

@media (min-width: 1024px) {
  .quiz-grid h2 {
    font-size: 1.6rem;
  }

  .category {
    font-size: 0.8rem;
  }

  p.questions {
    font-size: 1.125rem;
  }
}

.share-button {
  margin-top: 1em;
  border: none;
  padding: 0.5em 1em;
  border-radius: 1em;
  background: var(--button-bg-strong-blue);
  font-weight: bold;
  color: white;
  font-size: 0.8rem;
  cursor: pointer;
}

.share-button:hover {
  background: var(--button-bg-hover-blue);
}

</style>