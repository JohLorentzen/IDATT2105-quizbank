<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Quiz from '@/components/Quiz.vue';

import QuizGrid from '@/components/QuizGrid.vue';
import QuizFilter from '@/components/QuizFilter.vue';


const currentQuiz = ref(null);
const quizes = ref([]);
const filteredQuizes = ref([])
const router = useRouter();

const fetchQuizes = () => {
    axios.get('http://localhost:8080/rest/quiz', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    }).then(response => {
      quizes.value = response.data;
      filteredQuizes.value = response.data;
    }, error => {
      if (error.response && [401, 404].includes(error.response.status)) {
        router.push('/login')
      } else {
        console.log("Error fetching quizzes: " + error);
      }
    });
};

function filterChosenQuizes(categories) {
  if (categories.length === 0) {
    filteredQuizes.value = quizes.value;
    return;
  }

  filteredQuizes.value = [];
  for (const quiz of quizes.value) {
    for (const category of categories) {
      if (quiz.category === category) {
        filteredQuizes.value.push(quiz);
      }
    }
  }
}

onMounted(fetchQuizes);
</script>
<template>
  <div class="quiz-container">
    <h1>Quiz View</h1>
    <QuizGrid v-if="!currentQuiz" :quizes="filteredQuizes" @selectQuiz="currentQuiz = $event" />
    <QuizFilter v-if="quizes.length > 0 && !currentQuiz" @chosen-categories="filterChosenQuizes"/>
    <Quiz v-if="currentQuiz" :selectedQuiz="currentQuiz" @closeQuiz="closeQuiz" />
  </div>
</template>

<style scoped>

</style>
