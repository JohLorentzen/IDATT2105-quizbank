<script setup>
import { defineProps, ref } from 'vue';

// Define the props your component accepts
const props = defineProps({
  problem: String,
  solution: String
});

const userAnswer = ref('');

</script>

<template>
    <div>
        <h2>{{ props.problem }}</h2>
        <input type="text" v-model="userAnswer" placeholder="Enter your answer" />
        <button @click="$emit('correctAnswer', solution === userAnswer)">Submit</button>
    </div>
</template>

