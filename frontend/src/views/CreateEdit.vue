<script setup>
import CreateQuiz from '@/components/CreateQuiz.vue';
import QuizGrid from '@/components/QuizGrid.vue';
import EditQuiz from '@/components/EditQuiz.vue';
import endpoints from '@/endpoints.json';
import axios from 'axios';
import {onBeforeMount, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import {isUserLoggedIn} from "@/user-status.js";
import {useQuizStore} from "@/stores/quiz";

const router = useRouter();
const createQuiz = ref(false);
const quizzes = ref([]);
const currentQuiz = ref(null);
const deleteMode = ref(false);
const abortFetch = ref(true);
const quizStore = useQuizStore();

async function fetchQuizes() {
  if (abortFetch.value) {
    return;
  }
  fetchCategories();
  const url = `${endpoints.BASE_URL}${endpoints.MY_QUIZZES}`;
  try {
    const response = await axios.get(url, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    quizStore.setQuizes(response.data);
    quizzes.value = response.data;  
  } catch (error) {
    if (error.response && [401, 404].includes(error.response.status)) {
      router.push('/login');
    } else {
      console.error('Error fetching quizzes:', error);
    }
  }
};

function fetchCategories() {
    if (quizStore.getCategories.length > 0) {
      return;
    }
    const url = `${endpoints.BASE_URL}${endpoints.GET_CATEGORIES}`;
    axios.get( url, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    }).then(response => {
      quizStore.setCategories(response.data);
    }, error => {

      console.log("This is axios error " + error);
    })
};

const postQuiz = async (quizData) => {
  const url = `${endpoints.BASE_URL}${endpoints.GET_ALL_QUIZZES}`
  try {
    await axios.post(url, quizData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    
    fetchQuizes();
  } catch (error) {
    console.error('Error posting quiz:', error);
  }
  createQuiz.value = false;
};

const deleteQuiz = async (quizId) => {
  const url = `${endpoints.BASE_URL}${endpoints.GET_ALL_QUIZZES}/${quizId}`;
  try {
    await axios.delete(url, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    fetchQuizes();
  } catch (error) {
    console.error('Error deleting quiz:', error);
  }
  currentQuiz.value = null;
};
const handleQuizSubmit = (quizData) => {
  postQuiz(quizData);
};

const handleDeleteQuiz = (quizId) => {
  deleteQuiz(quizId);
};

const toggleDeleteMode = () => {
  deleteMode.value = !deleteMode.value;
};
const selectQuiz = (quiz) => {
  if (deleteMode.value) {
    const isConfirmed = confirm(`Are you sure you want to delete the quiz "${quiz.quizName}"?`);
    if (isConfirmed) {
      handleDeleteQuiz(quiz.quizId);
    }
  } else {
    currentQuiz.value = quiz;
  }
};
onBeforeMount(() => {
  abortFetch.value = !isUserLoggedIn();
})
onMounted(fetchQuizes);
</script>
<template>
  <main>
    <div class="create-edit">
      <h1>Quizes</h1>
      <div v-if="createQuiz">
        <CreateQuiz @submit="handleQuizSubmit"/>
      </div>
      <div v-else>
        <button @click="createQuiz = true"> Create quiz</button>
        <button @click="toggleDeleteMode">Delete quiz</button>
        <QuizGrid :quizzes="quizzes" @selectQuiz="selectQuiz"/>
      </div>
      <div v-if="currentQuiz">
        <EditQuiz :quiz="currentQuiz" @submit="handleQuizSubmit"/>
      </div>
    </div>
  </main>
</template>

<style scoped>
</style>
