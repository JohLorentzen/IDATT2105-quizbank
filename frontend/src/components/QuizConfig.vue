<script setup>
    import { ref, defineEmits } from 'vue';

    const quizName = ref('');
    const quizDescription = ref('');
    const quizQuestions = ref([]);
    const quizAnswers = ref([]);
    const quizDifficulty = ref('');
    const quizCategory = ref('');
    const quiz = ref({
      name: '',
      description: '',
      questions: [],
      difficulty: '',
      category: ''
    });
    const emit = defineEmits(['submitQuiz']);
    const submit = () => {
        emit('submitQuiz', quiz.value);
    }
</script>
<template> 
  <div>
    <form>
      <label for="quizName">Quiz Name</label>
      <input type="text" id="quizName" v-model="quizName" />
      <label for="quizDescription">Quiz Description</label>
      <input type="text" id="quizDescription" v-model="quizDescription" />
      <label for="quizQuestions">Quiz Questions</label>
      <input type="text" id="quizQuestions" v-model="quizQuestions" />
      <label for="quizAnswers">Quiz Answers</label>
      <input type="text" id="quizAnswers" v-model="quizAnswers" />
      <label for="quizDifficulty">Quiz Difficulty</label>
      <input type="text" id="quizDifficulty" v-model="quizDifficulty" />
      <label for="quizCategory">Quiz Category</label>
      <input type="text" id="quizCategory" v-model="quizCategory" />
      <button @click="submit" type="submit">Submit</button>
    </form>
  </div>
</template>
<style scoped>
  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
</style>