<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  question: Object
});

const emit = defineEmits(['submitAnswer']);

const answer = ref('');

const submit = () => {
  emit('submitAnswer', answer.value);
  answer.value = '';
};
</script>

<template>
  <div class="question-container">
    <p class="problem-statement">{{ question.problem }}</p>
    <div v-if="question.type === 'FILL_IN_THE_BLANKS'" class="answer-input-container">
      <input v-model="answer" type="text" placeholder="Your answer" @keyup.enter="submit" class="text-answer-input">
    </div>
    <div v-else-if="question.type === 'MULTIPLE_CHOICE'" class="choices-container">
      <div v-for="choice in question.choices" :key="choice" class="choice-option">
        <input type="radio" :value="choice" v-model="answer" :id="choice" class="radio-input">
        <label :for="choice" class="choice-label">{{ choice }}</label>
      </div>
    </div>
    <button @click="submit" class="submit-button">Submit</button>
  </div>
</template>


<style scoped>

.question-container {
  margin-bottom: 20px;
}

.choices-container {
  margin-top: 10px;
}

.choice-label {
  display: block;
  margin-bottom: 5px;
  cursor: pointer;
  user-select: none;
}

input[type="radio"] {
  margin-right: 10px;
}

input[type="text"] {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.submit-button {
  background-color: #0056b3;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

.submit-button:hover {
  background-color: #004494;
}

</style>
