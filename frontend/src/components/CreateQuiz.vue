<script setup>
import { ref, computed } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

const quiz = ref({                                          
  quizId: null,
  quizName: "",
  createdByUserId: 1,
  difficultyLevel: "",
  category: "",
  questions: [],
});
const question = ref({
  questionId: null,
  problem: "",
  solution: "",
  type: "",
  choices: [],
});
const isQuestionValid = computed(() => {
  return question.value.problem && question.value.solution && question.value.type &&
         (question.value.type !== 'MULTIPLE_CHOICE' || question.value.choices.length > 0);
});
const isQuizValid = computed(() => {
  return quiz.value.quizName && quiz.value.difficultyLevel && quiz.value.category;
});
const alternativeInput = ref("");
const emit = defineEmits(["submitQuiz"]);
const submit = () => {
  if (!isQuizValid.value) {
    alert("Please fill all quiz fields and add at least one question.");
    return;
  }
  emit("submitQuiz", quiz.value);
};
const quizView = ref(true);
const buttonLabel = computed(() =>
  quizView.value ? "Add question" : "Quiz details"
);
const changeView = () => {
  quizView.value = !quizView.value;
};
const saveQuestion = (event) => {
  event.preventDefault();
  if (!isQuestionValid.value) {
    alert("Please fill all question fields and add at least one alternative for multiple choice questions.");
    return;
  }
  question.value.choices.push(question.value.solution);
  if (!question.value.choices.includes(question.value.solution)) {
    question.value.choices.push(question.value.solution);
  }
  quiz.value.questions.push(question.value);
  question.value = {
    questionId: null,
    problem: "",
    solution: "",
    type: "",
    choices: [],
  };
  alternativeInput.value = ""; // Refresh the input field
};
const addAlternative = (alternative) => {
  if (!alternative) {
    alert("Alternative cannot be empty.");
    return;
  }
  question.value.choices.push(alternative);
  alternativeInput.value = ""; // Refresh the input field
};
</script>
<template>
  <button @click="changeView">{{ buttonLabel }}</button>
  <button @click="submit" type="submit" :disabled="!isQuizValid">Save quiz</button>
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
      <select v-model="question.type">
        <option value="MULTIPLE_CHOICE">Multiple Choice</option>
        <option value="FILL_IN_THE_BLANKS">Fill in the blank</option>
      </select>
      <div v-if="question.type === 'MULTIPLE_CHOICE'">
        <label for="alternativeInput">Add alternatives</label>
        <input type="text" id="alternativeInput" v-model="alternativeInput" />
        <button type="button" @click="addAlternative(alternativeInput)">
          Add alternative
        </button>

        <p>alternatives</p>
        <ul>
          <li v-for="(answer, index) in question.choices" :key="index">
            {{ answer }}
          </li>
        </ul>
      </div>
      <button @click="saveQuestion" :disabled="!isQuestionValid">Save question</button>
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
