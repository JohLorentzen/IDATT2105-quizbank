<template>
  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="close">&times;</span>
      <h1>Revision history for '{{ props.quiz.quizName }}'</h1>
      <table>
        <thead>
        <tr>
          <th>Action</th>
          <th>Timestamp</th>
          <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="entry in auditLog" :key="entry.id">
          <td>{{ entry.action }}</td>
          <td>{{ new Date(entry.timestamp).toLocaleString() }}</td>
          <td>{{ entry.username }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import {ref, watchEffect} from 'vue';
import axios from 'axios';

const props = defineProps(['quiz']);
const emit = defineEmits(['close']);
const showModal = ref(true);
const auditLog = ref([]);

const fetchAuditLog = async (quizId) => {
  try {
    const response = await axios.get(`http://localhost:8080/rest/quiz/${quizId}/audit-log`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    auditLog.value = response.data;
  } catch (error) {
    console.error('Error fetching audit log:', error);
  }
};

watchEffect(() => {
  if (props.quiz) {
    showModal.value = true;
    fetchAuditLog(props.quiz.quizId);
  }
});

const close = () => {
  showModal.value = false;
  emit('close');
};
</script>

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
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 80%;
  max-width: 500px;
}

.modal-content h1 {
  margin-bottom: 20px;
}

.modal-content table {
  width: 100%;
  border-collapse: collapse;
}

.modal-content table th,
.modal-content table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.modal-content table th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>