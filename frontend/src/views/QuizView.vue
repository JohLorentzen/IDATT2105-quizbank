<script setup>
import QuestionComponent from '@/components/Question.vue';


</script>
<template>
    <div>
        <h1>Quiz View</h1>
        <div v-for="question in questions" :key="question.id">
            <QuestionComponent :question="question" />
        </div>
    </div>
</template>

<style scoped>
/* Add your custom styles here */
</style>