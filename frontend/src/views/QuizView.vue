<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user.js'; 
import { useQuizStore } from '../stores/quiz.js';
import { useRouter } from 'vue-router';
import Question from '@/components/Question.vue';

const quizStore = useQuizStore();
const quizes = ref([]);
const router = useRouter();
const userStore = useUserStore();
const playingQuiz = ref(false);

const setQuestions = (questions) => {
    quizStore.questions = questions;
};

onMounted(() => {
    axios.get('http://localhost:8080/rest/quiz', {
        headers: {
            Authorization: `Bearer ${userStore.getToken}` 
        }
    })
    .then(response => {
        quizes.value = response.data;
    })
    .catch(error => {
        if (error.response) {
            if (error.response.status === 401 || error.response.status === 404) {
                router.push('/login');
            } else {
                console.error('Error fetching quizes:', error);
            }
        }
    });
});

const playQuiz = (quizId) => {
    quizes.value.forEach(quiz => {
        if (quiz.quizId === quizId) {
            quizStore.setQuestions(quiz.questions);
            playingQuiz.value = true;
        }
    });
};

</script>

<template>
    <div>
        <h1>Quiz View</h1>
        <div v-if="playingQuiz">
            <Question :quizName="quizName" />
        </div>
        <div v-else class="quiz-grid">
            <div v-for="quiz in quizes" :key="quiz.quisId">
                <button @click="playQuiz(quiz.quizId)">
                    <div>
                        <h2>{{ quiz.quizName }}</h2>
                        <p>{{ quiz.category }}</p>
                        <p v-if="quiz.questions">{{ quiz.questions.length }} questions</p>
                        <p v-else>No questions</p>
                    </div>
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.quiz-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
}
</style>
