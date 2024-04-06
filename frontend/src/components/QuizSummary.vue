<script setup>
import {defineProps, defineEmits, ref, computed} from 'vue';

const props = defineProps({
  questions: Array,
  answers: Array,
});

const emit = defineEmits(['backToQuizes']);




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
        <td :class="answer.isCorrect ? 'correct' : 'incorrect'">
          {{ answer.submittedAnswer }}
        </td>
        <td>
          <!-- Display the solution only if the answer is incorrect, otherwise indicate correctness -->
          <span>{{ props.questions[index].solution }}</span>
        </td>
      </tr>
    </tbody>
  </table>
  <button @click="emit('backToQuizes')">Back to Quizes</button>
</template>


<style scoped>
.results-table {
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

</style>
