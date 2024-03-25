<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user.js'; // Adjust the path as necessary
import { useRouter } from 'vue-router';
import Quiz from '@/components/Quiz.vue';


const currentQuiz = ref(null);
const router = useRouter();
const quizes = ref([]);
const questions = ref([]);
const userStore = useUserStore();
const playingQuiz = ref(false);

onMounted(() => {
    axios.get('http://localhost:8080/rest/quiz', {
        headers: {
            Authorization: `Bearer ${userStore.getToken}` // Assuming getToken is the method to retrieve the token
        }
    })
    .then(response => {
        quizes.value = response.data;
    })
    .catch(error => {
        if (error.response.status === 401) {
            router.push('/login');
        } else if (error.response.status === 404) {
            router.push('/login');
        } else {
            console.error('Error fetching quizes:', error);
        }
    });
});

const playQuiz = (quizId) => {
    const selectedQuiz = quizes.value.find(quiz => quiz.quizId === quizId);
    if (selectedQuiz) {
        questions.value = selectedQuiz.questions;
        currentQuiz.value = selectedQuiz;
        playingQuiz.value = true;
    }
};
</script>

<template>
    <div>
        <h1>Quiz View</h1>
        <div v-if="!playingQuiz" class="quiz-grid">
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
        <div v-if="playingQuiz">
            <Quiz :questions="questions.value" />
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
