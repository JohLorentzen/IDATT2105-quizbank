<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Quiz from '@/components/Quiz.vue';
import QuizFilter from '@/components/QuizFilter.vue';

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
    <QuizFilter v-if="quizes.length > 0" :quizes="quizes"/>
    <div v-if="!currentQuiz" class="quiz-grid">
      <div v-for="quiz in quizes" :key="quiz.quizId">
        <button @click="currentQuiz = quiz">
          <div>
            <h2>{{ quiz.quizName }}</h2>
            <p>{{ quiz.category }}</p>
            <p>{{ quiz.questions ? `${quiz.questions.length} questions` : 'No questions' }}</p>
          </div>
        </button>
      </div>
    </div>
    <Quiz v-if="currentQuiz" :selectedQuiz="currentQuiz" @closeQuiz="closeQuiz" />
  </div>
</template>

<style scoped>
.quiz-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;
}

.quiz-grid div {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.quiz-grid button {
  width: 100%;
  text-align: left;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  cursor: pointer;
  outline: none;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: background-color 0.3s ease;
}

.quiz-grid button:hover,
.quiz-grid button:focus {
  background-color: #e9e9e9;
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.quiz-grid h2 {
  margin-top: 0;
  color: #333;
}

.quiz-grid p {
  margin: 5px 0;
  color: #666;
}

</style>
