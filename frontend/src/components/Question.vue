<script>
import { defineProps, ref } from 'vue';
 const props = defineProps({
    question: String,
    solution: String,
    submitAnswer: Function,
  });
  function submitAnswer() {
    if (answer === solution) {
      alert('Correct!');
    } else {
      alert('Incorrect!');
    }
  }
  
</script>
<template> 
    <div>
        <h2>{{ question }}</h2>
        <input type="text" v-model="answer" placeholder="Enter your answer" />
        <button @click="submitAnswer">Submit</button>
    </div>
</template>

<style scoped>
/* Add your component styles here */
</style>