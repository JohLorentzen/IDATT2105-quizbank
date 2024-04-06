<script setup>
import { isUserLoggedIn } from "@/user-status.js";
import {onBeforeMount, onMounted, ref} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import Quiz from '@/components/Quiz.vue';

import QuizGrid from '@/components/QuizGrid.vue';
import QuizFilter from '@/components/QuizFilter.vue';
import endpoints from "@/endpoints.json";


const currentQuiz = ref(null);
const quizes = ref([]);
const filteredQuizes = ref([])
const router = useRouter();
const abortFetch = ref(true);

function fetchQuizes() {
  if (abortFetch.value) {
    return;
  }

  const url = `${endpoints.BASE_URL}${endpoints.GET_ALL_QUIZZES}`

  axios.get(url, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  }).then(response => {
    quizes.value = response.data;
    filteredQuizes.value = response.data;
  }).catch(error => {
    if (error.response && [401, 404].includes(error.response.status)) {
      router.push('/login')
    } else {
      console.log("Error fetching quizzes: " + error);
    }
  });
};

function filterChosenQuizes(filter) {
  if (filter.length === 0) {
    filteredQuizes.value = quizes.value;
    return;
  }

  filteredQuizes.value = [];
  for (const quiz of quizes.value) {
    if (filter.some(f => quiz.category.includes(f) || quiz.questions.some(q => q.tags.some(t => t === f)))) {
      filteredQuizes.value.push(quiz);
    }
  }
}

onBeforeMount(() => {
  abortFetch.value = !isUserLoggedIn();
})
onMounted(fetchQuizes);
</script>
<template>
  <main>
    <QuizFilter v-if="quizes.length > 0 && !currentQuiz" @updateFilters="filterChosenQuizes"/>
    <QuizGrid v-if="!currentQuiz" :quizzes="filteredQuizes" @selectQuiz="currentQuiz = $event"/>
    <Quiz v-if="currentQuiz" :selectedQuiz="currentQuiz" @backToQuizes="currentQuiz = null" @closeQuiz="closeQuiz"/>
  </main>
</template>

<style scoped>
main {
  background: var(--bg-very-light-blue-shadow);
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  padding: 2em 0;
}

@media (min-width: 1480px) {
  main {
    grid-template-columns: 1fr 1280px 1fr;
  }
}
</style>
