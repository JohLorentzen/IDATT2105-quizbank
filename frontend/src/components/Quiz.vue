<script setup>
import { ref, computed } from 'vue';
import { useQuizStore } from '@/stores/quiz';
import Question from "@/components/Question.vue";
import QuizSummary from "@/components/QuizSummary.vue";
import axios from 'axios';
import endpoints from '@/endpoints.json';

const currentQuestionIndex = ref(0);
const answers = ref([]);
const quizCompleted = ref(false);
const correctAnswersCount = ref(0);
const showResults = ref(false);
const props = defineProps({
  selectedQuiz: Object
    }
);


const incorrectAnswersCount = computed(() => answers.value.length - correctAnswersCount.value);

const submitAnswer = (submittedAnswer) => {
  const currentQuestion = props.selectedQuiz.questions[currentQuestionIndex.value];
  let isCorrect = false;
  console.log(currentQuestion);
  if (currentQuestion.type === 'FILL_IN_THE_BLANKS') {
    isCorrect = submittedAnswer.trim().toLowerCase() === currentQuestion.solution.trim().toLowerCase();
  } else if (currentQuestion.type === 'MULTIPLE_CHOICE') {
    isCorrect = submittedAnswer === currentQuestion.solution;
  }
  else if (currentQuestion.type === 'TRUE_FALSE') {
    isCorrect = submittedAnswer === currentQuestion.solution;
  }
  
  if (isCorrect) {
    correctAnswersCount.value++;
  }

  answers.value.push({ submittedAnswer, isCorrect  });

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

  showResults.value = true;
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
    <QuizSummary v-else-if="showResults" :questions="selectedQuiz.questions" :answers="answers" />
    <div v-else>
      <button @click="restartQuiz" class="restart-quiz-button">Try again</button>
      <button @click="postGrade" class="check-results-button">Check Results</button>
    </div>
    
  </div>
</template>

<style scoped>
.quiz-container {
  grid-column: 2 / -2;
  grid-row: 1 / -1;
  max-width: 600px;
  margin: 0 auto;
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
