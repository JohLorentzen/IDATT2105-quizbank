<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  question: Object
});

const emit = defineEmits(['submitAnswer']);

const answer = ref('');

const submit = (submittedAnswer = answer.value) => {
  emit('submitAnswer', submittedAnswer);
  answer.value = '';
};

const getImageSrc = (base64Image) => {
  return base64Image ? `data:image/jpeg;base64,${base64Image}` : '';
};
</script>

<template>
  <div class="question-container">
    <p class="problem-statement">{{ question.problem }}</p>
    
    <!-- Image Display -->
    <div v-if="question.image" class="image-container">
      <img :src="getImageSrc(question.image)" alt="Question Image" />
    </div>

    <div v-if="question.type === 'FILL_IN_THE_BLANKS'" class="answer-input-container">
      <input v-model="answer" type="text" placeholder="Your answer" @keyup.enter="submit" class="text-answer-input">
      <button @click="submit" class="submit-button">Submit</button>
    </div>
    <div v-else-if="question.type === 'TRUE_FALSE'">
      <button @click="submit('TRUE')" class="true-button">True</button>
      <button @click="submit('FALSE')" class="false-button">False</button>
    </div>
    <div v-else-if="question.type === 'MULTIPLE_CHOICE'" class="choices-container">
      <div v-for="choice in question.choices" :key="choice" class="choice-option">
        <input type="radio" :value="choice" v-model="answer" :id="choice" class="radio-input">
        <label :for="choice" class="choice-label">{{ choice }}</label>
      </div>
      <button @click="submit" class="submit-button">Submit</button>
    </div>
  </div>
</template>
<style scoped>

.question-container {
  margin-bottom: 20px;
  color: #004494;
}

/* Style for the image container */
.image-container {
  text-align: center; /* Center the image */
  margin-top: 10px; /* Space above the image */
}

/* Style for the image */
.image-container img {
  max-width: 100%; /* Image should not exceed the width of the container */
  height: auto; /* Maintain aspect ratio */
  border-radius: 10px; /* Optional: adds rounded corners to the image */
}

.choices-container {
  margin-top: 10px;
}

.choice-label {
  color: #004494 ;
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

