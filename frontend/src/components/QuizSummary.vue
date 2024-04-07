<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  questions: Array,
  answers: Array,
});


const emit = defineEmits(['toQuizzes'])

function isCorrect(answer) {
  return answer.isCorrect ? 'correct' : 'incorrect';
}

function getScore() {
  const score = props.answers.filter(a => a.isCorrect).length;
  const numOfQuestions = props.questions.length;
  return `You got ${score} / ${numOfQuestions} correct answers`
}

</script>
<template>
  <table class="results-table">
    <thead>
      <tr>
        <th>Question</th>
        <th>Your Answer</th>
        <th>Correct Answer</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(answer, index) in props.answers" :key="index">
        <td>{{ props.questions[index].problem }}</td>
        <td :class="isCorrect(answer)">
          {{ answer.submittedAnswer }} ({{ isCorrect(answer) }})
        </td>
        <td>
          <!-- Display the solution only if the answer is incorrect, otherwise indicate correctness -->
          <span>{{ props.questions[index].solution }}</span>
        </td>
      </tr>
    </tbody>
  </table>
  <p class="score-summary">{{ getScore() }}</p>
  <div class="btn-container">
    <button @click="emit('toQuizzes')">Back to Quizes</button>
  </div>
</template>


<style scoped>
.results-table {
  margin-top: 1.2em;
  width: 100%;
  border-collapse: collapse;
}

.results-table th,
.results-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.results-table th {
  background-color: #f4f4f4;
}

.correct {
  background-color: #80ed99;
}

.incorrect {
  background-color: #ffa69e;
}

.score-summary {
  margin-top: 1em;
  font-size: 1.4rem;
  text-align: center;
  font-weight: bold;
}

button {
  display: block;
  margin: 2em auto 0;
  font-weight: bold;
  border: none;
  cursor: pointer;
  padding: 1em 2em;
  border-radius: 0.6em;
  background-color: #0056b3;
  color: white;
}

button:hover {
  background-color: #004494;
}
</style>
