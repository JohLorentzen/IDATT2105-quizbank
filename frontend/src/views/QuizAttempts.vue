<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import endpoints from "@/endpoints.json";
import LineChart from "@/components/LineChart.vue";

const quizAttempts = ref([]);
const chartDataReal = ref([]);

const fetchQuizAttempts = () => {
  const url = `${endpoints.BASE_URL}${endpoints.GET_QUIZ_ATTEMPTS}/1`;
  //TODO: Change the hardcoded user id to the logged in user id
  axios.get(url, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  }).then(response => {
    quizAttempts.value = response.data;
    quizAttempts.value.sort((a, b) => new Date(b.attemptTime) - new Date(a.attemptTime));
    prepareChartData();
  }).catch(error => {
    // Error handling
  });
};

const prepareChartData = () => {
  //TODO: Group by difficulty level and put it in multiline chart
  let num = 0;
  const dataPoints = [];
  for (const attempt of quizAttempts.value) {
    num++;
    dataPoints.push({
      name: num,
      score: attempt.score,
      attemptTime: formatDate(attempt.attemptTime),
      quizName: attempt.quizName,
    });
  }

  chartDataReal.value = dataPoints;
};
const formatDate = (date) => {
  return new Date(date).toLocaleString();
};


onMounted(fetchQuizAttempts);
</script>

<template>
  <main>
    <div>
      <h2>My Quiz Attempts</h2>
      <line-chart :chartData="chartDataReal" style="height: 500px"/>

      <table class="quiz-table">
        <thead>
        <tr class="quiz-table-header">
          <th>Quiz Name</th>
          <th>Date Attempted</th>
          <th>Score</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="attempt in quizAttempts" :key="attempt.id" class="quiz-table-row">
          <td>{{ attempt.quizName }}</td>
          <td>{{ formatDate(attempt.attemptTime) }}</td>
          <td>{{ attempt.score.toFixed(2) }}%</td>
        </tr>
        </tbody>
      </table>

    </div>
  </main>
</template>

<style scoped>
.quiz-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  font-size: 0.9em;
}

.quiz-table-header th {
  background-color: #004085;
  color: #ffffff;
  text-align: left;
  padding: 12px 15px;
}

.quiz-table-row td {
  padding: 10px 15px;
  border-bottom: 1px solid #dddddd;
}

.quiz-table-row:nth-child(even) {
  background-color: #f2f2f2;
}

.quiz-table-row:hover {
  background-color: #ddd;
}

/* Responsive table */
@media screen and (max-width: 600px) {
  .quiz-table, .quiz-table-header, .quiz-table-row {
    display: block;
  }

  .quiz-table-row {
    margin-bottom: 10px;
  }

  .quiz-table-row td {
    display: block;
    text-align: right;
    padding-left: 50%;
    position: relative;
  }

  .quiz-table-row td::before {
    content: attr(data-label);
    position: absolute;
    left: 0;
    width: 50%;
    padding-left: 15px;
    font-weight: bold;
    text-align: left;
  }
}
</style>
