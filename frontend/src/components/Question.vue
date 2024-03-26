<script setup>
import { ref, computed } from 'vue';
import { useQuizStore } from '@/stores/quiz.js';

const props = defineProps({
    quizName: String
});
const quizStore = useQuizStore();
const question = ref(quizStore.getQuestion());
const userAnswer = ref('');
const correctAnswers = ref(0);
const length = ref(quizStore.getLength()); 
const isQuizFinished = ref(false); 

function submitAnswer(answer) {
    if (answer === question.value.solution) {
        correctAnswers.value++;
    }
    userAnswer.value = '';
    const nextQuestion = quizStore.getQuestion();
    if (nextQuestion === undefined) {
        isQuizFinished.value = true;
    } else {
        question.value = nextQuestion;
    }
}


const displayContent = computed(() => {
    if (isQuizFinished.value) {
        return 'summary';
    }
    return 'question';
});
</script>

<template>
    <div>
        <h1>{{ quizName }}</h1>
        <div v-if="displayContent === 'question'">
            <h2>{{ question.problem }}</h2>
            <label>Answer the question:</label>
            <input v-model="userAnswer" />
            <button @click="submitAnswer(userAnswer)">Next</button>
            <h3>Score: {{ correctAnswers }} / {{ length }}</h3>
        </div>
        <div v-else>
            <h2>Quiz Finished</h2>
            <p>Your final score is {{ correctAnswers }} out of {{ length }}.</p>
            <!-- Add additional summary information or actions here -->
        </div>
    </div>
</template>
