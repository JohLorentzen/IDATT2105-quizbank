<script setup>
    import { ref } from 'vue';
    import { useUserStore } from '@/stores/user';
    
    const userStore = useUserStore();

    const quiz = ref({
      quizId: null,
      quizName: '',
      // TODO: createdByUserId should be set to the current user's ID
      createdByUserId: 1,
      difficultyLevel: '',
      category: '',
      questions: []
    });
    const question = ref({
      questionId: null,
      problem: '',
      solution: '',
      type: '',
      choices: []
    });
    const emit = defineEmits(['submitQuiz']);
    const submit = () => {
        emit('submitQuiz', quiz.value);
    }
    const quizView = ref(true);

    const changeView = () => {
      quizView.value = !quizView.value;
    }
    const saveQuestion = (event) => {
      event.preventDefault();
      quiz.value.questions.push(question.value);
      question.value = {
        questionId: null,
        problem: '',
        solution: '',
        type: '',
        choices: []
      };
    }
</script>
<template>
  <button @click="changeView"> Add question  </button>
  <div v-if="quizView">
    <form>
      <label for="quizName">Quiz Name</label>
      <input type="text" id="quizName" v-model="quiz.quizName" />
      <label for="quizDifficulty">Quiz Difficulty</label>
      <select id="quizDifficulty" v-model="quiz.difficultyLevel">
        <option value="EASY">Easy</option>
        <option value="MEDIUM">Medium</option>
        <option value="HARD">Hard</option>
      </select>
      <label for="quizCategory">Quiz Category</label>
      <input type="text" id="quizCategory" v-model="quiz.category" />
    </form>
  </div>
  <div v-else>
    <form>
      <label for="questionProblem">Question Problem</label>
      <input type="text" id="questionProblem" v-model="question.problem" />
      <label for="questionSolution">Question Solution</label>
      <input type="text" id="questionSolution" v-model="question.solution" />
      <label for="questionType">Question Type</label>
      <select>
        <option value="MULTIPLE_CHOICE">Multiple Choice</option>
        <option value="FILL_IN_THE_BLANKS">Fill in the blank</option>
      </select>
      <button @click="saveQuestion">Save question</button>
    </form>
  </div>
  <button @click="submit" type="submit">Save quiz</button>
</template>
<style scoped>
  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
</style>