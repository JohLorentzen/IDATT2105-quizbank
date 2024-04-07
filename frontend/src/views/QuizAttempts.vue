<script setup>
import {ref, onMounted, onBeforeMount} from 'vue';
import axios from 'axios';
import endpoints from "@/endpoints.json";
import LineChart from "@/components/LineChart.vue";
import {isUserLoggedIn} from "@/user-status.js";

const quizAttempts = ref([]);
const chartDataReal = ref([]);
const abortFetch = ref(true);

const fetchQuizAttempts = () => {
  if (abortFetch.value) {
    return
  }

  const url = `${endpoints.BASE_URL}${endpoints.GET_QUIZ_ATTEMPTS}`;
  axios.get(url, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  }).then(response => {
    quizAttempts.value = response.data;
    // Sort by attempt time in ascending order
    quizAttempts.value.sort((a, b) => new Date(a.attemptTime) - new Date(b.attemptTime));
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

onBeforeMount(() => {
  abortFetch.value = !isUserLoggedIn();
})
onMounted(fetchQuizAttempts);
</script>

<template>
  <main>
    <div class="content-container">
      <div class="chart-container">
        <h2>My Quiz Attempts</h2>
        <line-chart :chartData="chartDataReal" style="height: 500px;"></line-chart>
      </div>

      <div class="table-container">
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
    </div>
  </main>
</template>

<style scoped>
.content-container {
  display: flex;
  justify-content: space-between;
}

.chart-container {
  width: 50%;
}

.table-container {
  width: 50%;
}

@media screen and (max-width: 600px) {
  .content-container {
    flex-direction: column;
  }

  .chart-container, .table-container {
    width: 100%;
  }
}


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

</style>
