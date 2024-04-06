<script setup>
import {computed, ref} from 'vue';
import ShareModal from './ShareModal.vue';

const props = defineProps(['quizzes']);
const emit = defineEmits(['selectQuiz']);
const currentQuiz = ref(null);
const sharedQuiz = ref(null);
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
  sharedQuiz.value = quiz;
  showModal.value = true;
}

function closeModal() {
  sharedQuiz.value = null;
  showModal.value = false;
}
</script>

<template>
  <h1>{{ availableQuizzesTitle }}</h1>
  <div v-if="!currentQuiz" class="quiz-grid">
    <div class="quiz-card" v-for="quiz in quizzes" :key="quiz.quizId" @click="playQuiz(quiz)">
      <h2>{{ quiz.quizName }}</h2>
      <p class="category">{{ quiz.category }}</p>
      <div class="questions-and-share">
        <button @click.stop="shareQuiz(quiz)" class="share-button">Share</button>
        <p class="questions">{{ quiz.questions ? `${quiz.questions.length} questions` : 'No questions' }}</p>
      </div>
    </div>
  </div>
  <ShareModal v-if="showModal" :quiz="sharedQuiz" @close="closeModal" />
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
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  grid-auto-rows: 120px;
  gap: 2em;
}

.quiz-card {
  background: var(--bg-very-light-blue);
  padding: 1em;
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: space-between;
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
  font-size: 0.54rem;
  padding: 0.5em 1em;
  border-radius: 0.4em;
  font-weight: bold;
  color: var(--text-color-light-grey);
  border: 1px solid var(--text-color-light-grey);
}

.questions-and-share {
  display: flex;
  justify-content: space-between;
  align-items: end;
  width: 100%;
}

p.questions {
  font-size: 0.70rem;
  color: var(--text-color-grey);
}

.share-button {
   border: none;
   padding: 0.5em 1em;
   border-radius: 1.4em;
   background: var(--button-bg-strong-blue);
   font-weight: bold;
   color: white;
   font-size: 0.8rem;
   cursor: pointer;
 }

.share-button:hover {
  background: var(--button-bg-hover-blue);
}

@media (min-width: 720px) {
  .quiz-grid {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }

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
    font-size: 0.9rem;
  }

  .share-button {
    font-size: 0.9rem;
    padding: 0.6em 1.2em;
    border-radius: 2.4em;
  }
}

@media (min-width: 1024px) {
  .quiz-grid {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
  .quiz-grid h2 {
    font-size: 1.6rem;
  }

  .category {
    font-size: 0.8rem;
  }

  p.questions {
    font-size: 1rem;
  }
}
</style>