<script setup>
import { ref } from 'vue';

const props = defineProps({
  quiz: Object
});

const emit = defineEmits(['submit']);
const quiz = ref(props.quiz);
const submit = () => {
  emit('submit', quiz.value);
};

</script>
<template>
  <div>
    <h2>{{ quiz.quizName }}</h2>
    <p>Category: {{ quiz.category }}</p>
    <p>Difficulty: {{ quiz.difficultyLevel }}</p>
    <p>Questions: {{ quiz.questions.length }}</p>
    <input type="text" v-model="quiz.quizName" />
    <input type="text" v-model="quiz.category" />
    <select v-model="quiz.difficultyLevel">
      <option value="EASY">Easy</option>
      <option value="MEDIUM">Medium</option>
      <option value="HARD">Hard</option>
    </select>

    <div v-for="question in quiz.questions" :key="question.questionId">
      <h3>{{ question.problem }}</h3>
      <input type="text" v-model="question.problem" placeholder="New question" />
      <p>{{ question.solution }}</p>
      <input type="text" v-model="question.solution" placeholder="New solution" />
      <p>{{ question.type }}</p>
      <select v-model="question.type" >
        <option value="FILL_IN_THE_BLANKS">Fill in the blanks</option>
        <option value="MULTIPLE_CHOICE">Multiple choice</option>
      </select>
      <ul>
        <li v-for="choice in question.choices" :key="choice">{{ choice }}</li>
      </ul>
    </div>

    <button @click="submit">Submit</button>
    
  </div>
</template>
