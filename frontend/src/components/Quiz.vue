<script setup>
import { ref } from 'vue';
import Question from '@/components/Question.vue';

const props = defineProps({
  questions: Array
});

const correctAnswers = ref(0);
const currentQuestionIndex = ref(0);

const handleAnswer = (isCorrect) => {
    if (currentQuestionIndex.value < props.questions.length - 1) {
        currentQuestionIndex.value++;
        if (isCorrect) {
            correctAnswers.value++;
        } else {
            // Handle incorrect answer logic here if needed
        }
    } else {
        // Handle quiz completion logic here if needed
    }
};

</script>

<template>
    <div>
        <div v-if="currentQuestionIndex < props.questions.length">
            <Question 
                :problem="props.questions[currentQuestionIndex].problem" 
                :solution="props.questions[currentQuestionIndex].solution" 
                @submitAnswer="handleAnswer" 
            />
        </div>
        <div v-else>
            <h2>Quiz Complete!</h2>
        </div>
    </div>
</template>

