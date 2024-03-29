<script setup>
import CreateQuiz from '@/components/CreateQuiz.vue';
import QuizGrid from '@/components/QuizGrid.vue';
import EditQuiz from '@/components/EditQuiz.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue'; 
import { useRouter } from 'vue-router';


const path = ref('create');
const currentQuiz = ref(null);
const quizes = ref([]);
const router = useRouter();
const createQuiz = ref(false);
const newQuiz = ref(null);

const fetchQuizes = async () => {
  try {
    const response = await axios.get('http://localhost:8080/rest/quiz/user/1', {
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

const createNewQuiz = () => {;
    createQuiz.value = true;
    path.value = 'create';
}

const postNewQuiz = async () => {
    try {
        const response = await axios.post('http://localhost:8080/rest/quiz', newQuiz.value, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });
        newQuiz.value = response.data;
        createQuiz.value = false;
        fetchQuizes();
    } catch (error) {
        if (error.response && [401, 404].includes(error.response.status)) {
        } else {
            console.error('Error fetching quizzes:', error);
        }
    }
}
const handleQuizSubmit = () => {
  newQuiz.value = currentQuiz.value;
  postNewQuiz();
}
onMounted(fetchQuizes);
</script>
<template>
    <div v-if="createQuiz"> 
      <CreateQuiz @submitQuiz="handleQuizSubmit"/>
    </div>
    <div v-else>        
        <button @click="createNewQuiz"> Create quiz</button>
        <QuizGrid :quizes="quizes" @selectQuiz="currentQuiz = $event"/>
    </div>
    <div v-if="currentQuiz">
        <EditQuiz :quiz="currentQuiz" @submit="handleQuizSubmit"/>
    </div>
    
</template>