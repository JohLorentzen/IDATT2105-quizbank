<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Quiz from '@/components/Quiz.vue';
import QuizGrid from '@/components/QuizGrid.vue';

const currentQuiz = ref(null);
const quizes = ref([]);
const router = useRouter();

const fetchQuizes = async () => {
  try {
    const response = await axios.get('http://localhost:8080/rest/quiz', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    quizes.value = response.data;
  } catch (error) {
    if (error.response && [401, 404].includes(error.response.status)) {
      router.push('/login');
    } else {
      console.error('Error fetching quizzes:', error);
    }
  }
};

onMounted(fetchQuizes);
</script>


<template>
  <div class="quiz-container">
    <h1>Quiz View</h1>
    <QuizGrid v-if="!currentQuiz" :quizes="quizes" @selectQuiz="currentQuiz = $event" />
    <Quiz v-if="currentQuiz" :selectedQuiz="currentQuiz" @closeQuiz="closeQuiz" />
  </div>
</template>

<style scoped>

</style>
