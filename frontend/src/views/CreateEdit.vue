<script setup>
import QuizConfig from '@/components/QuizConfig.vue';
import QuizGrid from '@/components/QuizGrid.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue'; 
import { useRouter } from 'vue-router';


const path = ref('create');
const currentQuiz = ref(null);
const quizes = ref([]);
const router = useRouter();

const fetchQuizes = async () => {
  try {
    const response = await axios.get('http://localhost:8080/rest/quiz/1', {
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
    <div> 
         <button>Create quiz</button>
        <button>Edit quiz</button>
    </div>
    <div>
        <h1>Create or edit your quiz</h1>
        <QuizGrid v-if="!currentQuiz" :quizes="quizes" @selectQuiz="currentQuiz = $event"/>
    </div>
    
</template>