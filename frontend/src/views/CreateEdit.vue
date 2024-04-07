<script setup>
import CreateQuiz from '@/components/CreateQuiz.vue';
import QuizGrid from '@/components/QuizGrid.vue';
import EditQuiz from '@/components/EditQuiz.vue';
import endpoints from '@/endpoints.json';
import axios from 'axios';
import {computed, onBeforeMount, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import {isUserLoggedIn} from "@/user-status.js";

const router = useRouter();
const quizzes = ref([]);
const createQuiz = ref(false);
const currentQuiz = ref(null);
const deleteMode = ref(false);
const abortFetch = ref(true);

const titleText = computed(() => {
  if (createQuiz.value) {
    return "Create Quiz"
  }

  const numberOfQuizzes = quizzes.value.length;
  return `You have ${numberOfQuizzes} quizzes`
})

async function fetchQuizes() {
  console.log(abortFetch.value);
  if (abortFetch.value) {
    return;
  }

  const url = `${endpoints.BASE_URL}${endpoints.MY_QUIZZES}`;
  try {
    const response = await axios.get(url, {
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
}

const postQuiz = async (quizData) => {

  const url = `${endpoints.BASE_URL}${endpoints.GET_ALL_QUIZZES}`
  try {
    await axios.post(url, quizData, {
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
  router.push('/createEdit');
};

const toggleDeleteMode = () => {
  deleteMode.value = !deleteMode.value;
};
const selectQuiz = (quiz) => {
  if (deleteMode.value) {
    const isConfirmed = confirm(`Are you sure you want to delete the quiz "${quiz.quizName}"?`);
    if (isConfirmed) {
      deleteQuiz(quiz.quizId);
    }
    deleteMode.value = false;
  } else {
    currentQuiz.value = quiz;
  }
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
};

onBeforeMount(() => {
  abortFetch.value = !isUserLoggedIn();
})
onMounted(fetchQuizes);
</script>
<template>
  <main>
    <div class="create-edit">
      <h1>{{ titleText }}</h1>
      <CreateQuiz v-if="createQuiz" @submitQuiz="handleQuizSubmit"/>
      <div v-else>
        <p>Get started by creating your own quiz</p>
        <button @click="createQuiz = true"> Create quiz</button>
        <button v-if="quizzes.length > 0" @click="toggleDeleteMode">Delete quiz</button>
        <QuizGrid :quizzes="quizzes" @selectQuiz="selectQuiz"/>
      </div>
      <div v-if="currentQuiz">
        <EditQuiz :quiz="currentQuiz" @submit="handleQuizSubmit"/>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  background-color: var(--bg-very-light-blue-shadow);
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  grid-column: 1 / -1;
  padding: 2em 0;
}

h1 {
  font-weight: bold;
  font-size: 2.6em;
}

.create-edit {
  grid-column: 2 / -2;
}
</style>
