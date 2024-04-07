<script setup>
import {ref, computed, defineEmits} from 'vue';
import {useQuizStore} from '@/stores/quiz';
import Question from "@/components/Question.vue";
import QuizSummary from "@/components/QuizSummary.vue";
import axios from 'axios';
import endpoints from '@/endpoints.json';
import router from "@/router/index.js";

const currentQuestionIndex = ref(0);
const answers = ref([]);
const quizCompleted = ref(false);
const correctAnswersCount = ref(0);
const showResults = ref(false);
const props = defineProps({
      selectedQuiz: Object
    }
);
const emit = defineEmits('closeQuiz')

const finishedQuizTitle = computed(() => {
  return (showResults.value) ? "Results" : "Quiz Completed"
})

const incorrectAnswersCount = computed(() => answers.value.length - correctAnswersCount.value);

const submitAnswer = (submittedAnswer) => {
  const currentQuestion = props.selectedQuiz.questions[currentQuestionIndex.value];
  let isCorrect = false;
  if (currentQuestion.type === 'FILL_IN_THE_BLANKS') {
    isCorrect = submittedAnswer.trim().toLowerCase() === currentQuestion.solution.trim().toLowerCase();
  } else if (currentQuestion.type === 'MULTIPLE_CHOICE') {
    isCorrect = submittedAnswer === currentQuestion.solution;
  } else if (currentQuestion.type === 'TRUE_FALSE') {
    isCorrect = submittedAnswer === currentQuestion.solution;
  }

  if (isCorrect) {
    correctAnswersCount.value++;
  }

  answers.value.push({submittedAnswer, isCorrect});

  if (currentQuestionIndex.value < props.selectedQuiz.questions.length - 1) {
    currentQuestionIndex.value++;
  } else {
    quizCompleted.value = true;
    postGrade();
  }
};

const restartQuiz = () => {
  currentQuestionIndex.value = 0;
  answers.value = [];
  quizCompleted.value = false;
  showResults.value = false;
  correctAnswersCount.value = 0;
};

function postGrade() {

  const url = `${endpoints.BASE_URL}${endpoints.POST_ATTEMPT}`;

  const quizAttempt = {
    quizId: props.selectedQuiz.quizId,
    totalQuestions: props.selectedQuiz.questions.length,
    correctAnswers: correctAnswersCount.value,
  };

  axios.post(url, {
    quizId: props.selectedQuiz.quizId,
    totalQuestions: props.selectedQuiz.questions.length,
    correctAnswers: correctAnswersCount.value,
  }, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  })
      .catch((error) => {
        console.log(error);
      });
}

</script>

<template>
  <div class="component-container">
    <div class="quiz-container">
      <h2 v-if="!quizCompleted" class="quiz-header">{{ selectedQuiz.quizName }}</h2>
      <h2 v-else class="finished-header">{{ finishedQuizTitle }}</h2>
      <div v-if="!quizCompleted">
        <Question
            v-if="selectedQuiz.questions.length"
            :key="currentQuestionIndex"
            :question="selectedQuiz.questions[currentQuestionIndex]"
            @submitAnswer="submitAnswer"
            class="question-container"
        />
      </div>
      <QuizSummary v-else-if="showResults" :questions="selectedQuiz.questions" :answers="answers" @to-quizzes="emit('closeQuiz')"/>
      <div v-else class="finished-buttons">
        <button @click="restartQuiz" class="restart-quiz-button">Try again</button>
        <button @click="postGrade" class="check-results-button">Check Results</button>
        <button @click="emit('closeQuiz')" class="back-to-quizes-button">Back to Quizes</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.component-container {
  grid-column: 2 / -2;
  height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.quiz-container {
  max-width: 600px;
  padding: 2em;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.quiz-header {
  font-size: 1rem;
  color: var(--text-color-light-grey)
}

.finished-header {
  text-align: center;
  font-size: 3rem;
  font-weight: bold;
}

.finished-buttons {
  margin-top: 2em;
  display: flex;
  justify-content: center;
  gap: 2em;
}

button {
  font-weight: bold;
  border: none;
  cursor: pointer;
  padding: 1em 2em;
  border-radius: 0.6em;
}

.check-results-button {
  background-color: #0056b3;
  color: white;
}

.check-results-button:hover {
  background-color: #004494;
}

.restart-quiz-button:hover {
  background-color: var(--text-color-light-grey);
  color: var(--text-color-white)
}

.quiz-results h3 {
  color: #0056b3;
  margin-bottom: 15px;
}

.quiz-results p {
  font-size: 16px;
  margin: 5px 0;
}
</style>
