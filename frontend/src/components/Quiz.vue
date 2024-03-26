<script setup>
import { ref, computed } from 'vue';
import Question from "@/components/Question.vue";

const currentQuestionIndex = ref(0);
const answers = ref([]);
const quizCompleted = ref(false);
const correctAnswersCount = ref(0);
const props = defineProps({
  selectedQuiz: Object
    }
);


const incorrectAnswersCount = computed(() => answers.value.length - correctAnswersCount.value);

const submitAnswer = (submittedAnswer) => {
  const currentQuestion = props.selectedQuiz.questions[currentQuestionIndex.value];
  let isCorrect = false;

  if (currentQuestion.type === 'FILL_IN_THE_BLANKS') {
    isCorrect = submittedAnswer.trim().toLowerCase() === currentQuestion.solution.trim().toLowerCase();
  } else if (currentQuestion.type === 'MULTIPLE_CHOICE') {
    isCorrect = submittedAnswer === currentQuestion.solution;
  }

  if (isCorrect) {
    correctAnswersCount.value++;
  }

  answers.value.push({ submittedAnswer, isCorrect });

  if (currentQuestionIndex.value < props.selectedQuiz.questions.length - 1) {
    currentQuestionIndex.value++;
  } else {
    quizCompleted.value = true;
  }
};

const restartQuiz = () => {
  currentQuestionIndex.value = 0;
  answers.value = [];
  quizCompleted.value = false;
  correctAnswersCount.value = 0;
};
</script>

<template>
  <div class="quiz-container">
    <h2 class="quiz-header">{{ selectedQuiz.quizName }}</h2>
    <div v-if="!quizCompleted">
      <Question
          v-if="selectedQuiz.questions.length"
          :key="currentQuestionIndex"
          :question="selectedQuiz.questions[currentQuestionIndex]"
          @submitAnswer="submitAnswer"
          class="question-container"
      />
    </div>
    <div v-else class="quiz-results">
      <h3>Quiz Results</h3>
      <p>You answered {{ correctAnswersCount }} questions correctly!</p>
      <p>You got {{ incorrectAnswersCount }} questions incorrect.</p>
      <button @click="restartQuiz" class="try-again-button">Try Again</button>
    </div>
  </div>
</template>



<style scoped>
.quiz-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.quiz-header {
  margin-bottom: 20px;
}

.question-container {
  margin-bottom: 20px;
}


.quiz-results {
  text-align: center;
}

.quiz-results h3 {
  color: #0056b3;
  margin-bottom: 15px;
}

.quiz-results p {
  font-size: 16px;
  margin: 5px 0;
}

.try-again-button {
  background-color: #28a745;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

.try-again-button:hover {
  background-color: #218838;
}
</style>
