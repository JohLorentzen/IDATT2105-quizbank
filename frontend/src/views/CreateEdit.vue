<script setup>
import CreateQuiz from '@/components/CreateQuiz.vue';
import QuizGrid from '@/components/QuizGrid.vue';
import EditQuiz from '@/components/EditQuiz.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue'; 
import { useRouter } from 'vue-router';

const router = useRouter();
const quizzes = ref([]);
const createQuiz = ref(false);
const currentQuiz = ref(null);
const deleteMode = ref(false);

const fetchQuizes = async () => {
  try {
    const response = await axios.get('http://localhost:8080/rest/quiz/user/1', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    quizzes.value = response.data;
  } catch (error) {
    if (error.response && [401, 404].includes(error.response.status)) {
      router.push('/login');
    } else {
      console.error('Error fetching quizzes:', error);
    }
  }
};

const postQuiz = async (quizData) => {
    try {
        await axios.post('http://localhost:8080/rest/quiz', quizData, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });
        createQuiz.value = false;
        fetchQuizes();
    } catch (error) {
        console.error('Error posting quiz:', error);
    }
};

const handleQuizSubmit = (quizData) => {
    postQuiz(quizData);
};

const toggleDeleteMode = () => {
  deleteMode.value = !deleteMode.value;
};
const selectQuiz = (quiz) => {
  if (deleteMode.value) {
    const isConfirmed = confirm(`Are you sure you want to delete the quiz "${quiz.quizName}"?`);
    if (isConfirmed){
      deleteQuiz(quiz.quizId);
    }
    deleteMode.value = false; 
  } else {
    currentQuiz.value = quiz;
  }
};

const deleteQuiz = async (quizId) => {
    try {
        await axios.delete(`http://localhost:8080/rest/quiz/${quizId}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });
        fetchQuizes();
    } catch (error) {
        console.error('Error deleting quiz:', error);
    }
};

onMounted(fetchQuizes);
</script>
<template>
  <h1>Quizes</h1>
  <div v-if="createQuiz"> 
    <CreateQuiz @submitQuiz="handleQuizSubmit"/>
  </div>
  <div v-else>        
      <button @click="createQuiz = true"> Create quiz</button>
      <button @click="toggleDeleteMode">Delete quiz</button>
      <QuizGrid :quizzes="quizzes" @selectQuiz="selectQuiz"/>
  </div>
  <div v-if="currentQuiz">
      <EditQuiz :quiz="currentQuiz" @submit="handleQuizSubmit"/>
  </div>
</template>
