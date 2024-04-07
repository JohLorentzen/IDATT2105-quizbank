<script setup>
import { ref } from 'vue';
import axios from 'axios';

const props = defineProps(['quiz']);
const emit = defineEmits(['close']);
const shareUserName = ref('');
const viewerOrEditor = ref('viewer');

const shareQuiz = async () => {
  try {
    await axios.post(`http://localhost:8080/rest/quiz/${props.quiz.quizId}/share?userName=${shareUserName.value}&role=${viewerOrEditor.value}`, {}, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    alert('Quiz shared successfully');
    emit('close');
  } catch (error) {
    alert('Error sharing quiz: ' + error.response.data)
    console.error('Error sharing quiz:', error);
  }
};
</script>

<template>
  <div class="modal" @click="emit('close')">
    <div class="modal-content" @click.stop="">
      <h2>Share quiz</h2>
      <p>Share '{{quiz.quizName}}' with another user</p>
      <div class="input-container">
        <input v-model="shareUserName" placeholder="Username" />
        <select v-model="viewerOrEditor">
          <option value="viewer">Viewer</option>
          <option value="editor">Editor</option>
        </select>
      </div>
      <div class="button-container">
        <button @click="shareQuiz">Share</button>
        <button @click="emit('close')" style="float: right; background: darkred">Close</button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
  z-index: 1000; /* Ensure the modal is above other elements */
}

.modal-content {
  display: flex;
  flex-direction: column;
  background-color: white;
  border-radius: 10px;
  text-align: center;
  padding: 1.4em 2em;
}

h2 {
  font-weight: bold;
  font-size: 2.4rem;
}

p {
  margin-top: 1em;
}

.input-container {
  margin-top: 1em;
  display: flex;
  width: 100%;
  gap: 0.4em;
  align-items: center;
  justify-content: center;
}

.button-container {
  display: flex;
  width: 100%;
  gap: 1em;
  justify-content: center;
  margin-top: 0.8em;
}

button {
  border: none;
  padding: 0.5em 1em;
  border-radius: 1.4em;
  background: var(--button-bg-strong-blue);
  font-weight: bold;
  color: white;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background: var(--button-bg-hover-blue);
}

button:active {
  background: var(--button-bg-active-blue);
}

input {
  padding: 0.5em;
  margin: 0.5em 0;
  border: 1px solid var(--border-very-light-blue);
  border-radius: 0.5em;
}

select {
  padding: 0.5em;
  border: 1px solid var(--border-very-light-blue);
  border-radius: 0.5em;
}
</style>