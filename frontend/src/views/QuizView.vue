<script setup>
import {isUserLoggedIn} from "@/user-status.js";
import {onBeforeMount, onMounted, ref, watch} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import Quiz from '@/components/Quiz.vue';
import QuizGrid from '@/components/QuizGrid.vue';
import QuizFilter from '@/components/QuizFilter.vue';
import endpoints from "@/endpoints.json";
import {useUserStore} from "@/stores/user";

const currentQuiz = ref(null);
const quizes = ref([]);
const filteredQuizes = ref([]);
const router = useRouter();
const abortFetch = ref(true);
const userStore = useUserStore();

const selectedTagsCategories = ref([]);
const selectedDifficulty = ref('');
const selectedSharedStatus = ref(false);

onBeforeMount(() => {
  abortFetch.value = !isUserLoggedIn();
})

onMounted(fetchQuizes);

watch([selectedTagsCategories, selectedDifficulty, selectedSharedStatus], () => {
  applyAllFilters();
}, {deep: true});

function fetchQuizes() {
  if (abortFetch.value) {
    return;
  }

  const url = `${endpoints.BASE_URL}${endpoints.GET_ALL_QUIZZES}`;

  axios.get(url, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  }).then(response => {
    quizes.value = response.data;
    applyAllFilters();
  }).catch(error => {
    if (error.response && [401, 404].includes(error.response.status)) {
      router.push('/login');
    } else {
      console.log("Error fetching quizzes: " + error);
    }
  });
}

function applyAllFilters() {
  let result = quizes.value;

  if (selectedTagsCategories.value.length > 0) {
    result = result.filter(quiz =>
        selectedTagsCategories.value.some(f =>
            quiz.category.includes(f) || quiz.questions.some(q => q.tags.includes(f))
        )
    );
  }

  if (selectedDifficulty.value) {
    result = result.filter(quiz => quiz.difficultyLevel.toLowerCase() === selectedDifficulty.value.toLowerCase());
  }

  if (selectedSharedStatus.value) {
    result = result.filter(quiz => quiz.sharedUsers.includes(userStore.getUsername));
  }

  filteredQuizes.value = result;
}

function updateFilters(tagsCategories) {
  selectedTagsCategories.value = tagsCategories;
}

function updateDifficulty(difficulty) {
  selectedDifficulty.value = difficulty;
}

function updateSharedStatus(sharedStatus) {
  selectedSharedStatus.value = sharedStatus;
}
</script>


<template>
  <main>
    <QuizFilter
        v-if="quizes.length > 0 && !currentQuiz"
        @updateFilters="updateFilters"
        @updateDifficultyFilter="updateDifficulty"
        @updateSharedStatusFilter="updateSharedStatus"
        style="margin-bottom: 100px"/>
    <QuizGrid
        v-if="!currentQuiz"
        :quizzes="filteredQuizes"
        @selectQuiz="currentQuiz = $event"/>
    <section v-if="currentQuiz">
      <button @click="currentQuiz = null" class="go-back">Go back</button>
      <Quiz
        :selectedQuiz="currentQuiz"
        @closeQuiz="currentQuiz = null"/>
    </section>
  </main>
</template>


<style scoped>
main {
  background: var(--bg-very-light-blue-shadow);
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  grid-auto-rows: minmax(min-content, max-content);
  padding: 2em 0;
}
.go-back {
  background: var(--button-bg-strong-blue);
  border: none;
  color: white;
  border-radius: 0.5em;
  padding: 0.5em 1em;
  margin: 1em 0;
  cursor: pointer;
}
.go-back:hover {
  background: var(--button-bg-hover-blue);
}

@media (min-width: 1480px) {
  main {
    grid-template-columns: 1fr 1280px 1fr;
  }
}
</style>
