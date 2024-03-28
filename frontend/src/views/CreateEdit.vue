<script setup>
import QuizConfig from '@/components/QuizConfig.vue';
import { ref } from 'vue'; 

const newQuiz = ref(null)

const submitQuiz = (quiz) => {
    newQuiz.value = quiz;
}
</script>
<template>
    
    <div>
        <h1>Create or edit your quiz</h1>
        <QuizConfig @submitQuiz="submitQuiz"/>
    </div>
    <div> 
         <button>Create quiz</button>
        <button>Edit quiz</button>
    </div>
</template>