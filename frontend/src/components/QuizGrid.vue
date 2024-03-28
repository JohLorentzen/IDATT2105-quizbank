<script setup>
    import { ref, onMounted } from 'vue';
    const props = defineProps(['quizes']);
    const emit = defineEmits(['selectQuiz']);
    const currentQuiz = ref(null);

    const selectQuiz = (quiz) => {
        currentQuiz.value = quiz;
        emit('selectQuiz', currentQuiz.value);
    };
</script>
<template>
    <div v-if="!currentQuiz" class="quiz-grid">
      <div v-for="quiz in quizes" :key="quiz.quizId">
        <button @click="selectQuiz">
          <div>
            <h2>{{ quiz.quizName }}</h2>
            <p>{{ quiz.category }}</p>
            <p>{{ quiz.questions ? `${quiz.questions.length} questions` : 'No questions' }}</p>
          </div>
        </button>
      </div>
    </div>
</template>
<style>
    .quiz-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;
}

.quiz-grid div {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.quiz-grid button {
  width: 100%;
  text-align: left;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  cursor: pointer;
  outline: none;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: background-color 0.3s ease;
}

.quiz-grid button:hover,
.quiz-grid button:focus {
  background-color: #e9e9e9;
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.quiz-grid h2 {
  margin-top: 0;
  color: #333;
}

.quiz-grid p {
  margin: 5px 0;
  color: #666;
}
</style>