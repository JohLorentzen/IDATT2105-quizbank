<script setup>
import { ref } from "vue";
import Papa from "papaparse";

const props = defineProps({
  quiz: Object,
});

const currentQuestionIndex = ref(0);
const emit = defineEmits(["submit"]);
const quiz = ref(props.quiz);
const newAlternative = ref("");

const submit = () => {
  emit("submit", quiz.value);
};

const addAlternative = () => {
  if (newAlternative.value.trim()) {
    quiz.value.questions[currentQuestionIndex.value].choices.push(newAlternative.value);
    newAlternative.value = '';
  }
};

const removeAlternative = (index) => {
  console.log(index);
  quiz.value.questions[currentQuestionIndex.value].choices.splice(index, 1);
};

const handleFileChange = (event, question) => {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => {
    question.image = reader.result.split(",")[1];
  };
};
const removeImage = (question) => {
  question.image = null;
};

const goToNextQuestion = () => {
    if(currentQuestionIndex.value < quiz.value.questions.length - 1) {
        currentQuestionIndex.value++;
    }
};

const goToPreviousQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};

const addQuestion = () => {
  const newQuestion = {
    problem: '', 
    solution: '', 
    type: 'FILL_IN_THE_BLANKS', 
    choices: [], 
    tags: [], 
    image: null 
  };
  quiz.value.questions.push(newQuestion);
  currentQuestionIndex.value = quiz.value.questions.length - 1; 
};

const removeQuestion = () => {

    quiz.value.questions.splice(currentQuestionIndex.value, 1);
    console.log(quiz.value.questions);
    if (currentQuestionIndex.value >= quiz.value.questions.length) {
      currentQuestionIndex.value = quiz.value.questions.length - 1; 
    }
};

const exportQuizToCSV = () => {
  const quizData = quiz.value.questions.map(q => ({
    'Problem': q.problem,
    'Solution': q.solution,
    'Choices': formatChoices(q)
  }));

  const csv = Papa.unparse({
    fields: ['Problem', 'Solution', 'Choices'], 
    data: quizData
  });

  const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
  const link = document.createElement('a');
  const url = URL.createObjectURL(blob);
  link.setAttribute('href', url);
  link.setAttribute('download', `${quiz.value.quizName.replace(/\s+/g, '_')}.csv`); 
  link.style.visibility = 'hidden';
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

const formatChoices = (question) => {
  if (question.type === 'TRUE_FALSE') {
    return 'True, False';
  }
  return question.choices.join(', ');
};
</script>
<template>
  <div>
    <h2>{{ quiz.quizName }}</h2>
    <p>Category: {{ quiz.category }}</p>
    <p>Difficulty: {{ quiz.difficultyLevel }}</p>
    <p>Questions: {{ quiz.questions.length }}</p>
    <input type="text" v-model="quiz.quizName" />
    <input type="text" v-model="quiz.category" />
    <select v-model="quiz.difficultyLevel">
      <option value="EASY">Easy</option>
      <option value="MEDIUM">Medium</option>
      <option value="HARD">Hard</option>
    </select>
    <div v-if="quiz.questions.length > 0">
      <div v-if="quiz.questions[currentQuestionIndex]">
        <h3>{{ quiz.questions[currentQuestionIndex].problem }}</h3>
        <input
          type="text"
          v-model="quiz.questions[currentQuestionIndex].problem"
          placeholder="New question"
        />
        <p>{{ quiz.questions[currentQuestionIndex].solution }}</p>
        <input
          type="text"
          v-model="quiz.questions[currentQuestionIndex].solution"
          placeholder="New solution"
        />
        <p>{{ quiz.questions[currentQuestionIndex].type }}</p>
        <select v-model="quiz.questions[currentQuestionIndex].type">
          <option value="FILL_IN_THE_BLANKS">Fill in the blanks</option>
          <option value="MULTIPLE_CHOICE">Multiple choice</option>
          <option value="TRUE_FALSE">True or False</option>
        </select>
        <!-- Multiple choice alternatives -->
        <div v-if="quiz.questions[currentQuestionIndex].type === 'MULTIPLE_CHOICE'">
          <ul>
            <li v-for="(choice, index) in quiz.questions[currentQuestionIndex].choices" :key="index">
              {{ choice }}
              <button @click="removeAlternative(index)">Remove</button>
            </li>
          </ul>
          <input type="text" v-model="newAlternative" placeholder="New alternative">
          <button @click="addAlternative">Add Alternative</button>
        </div>
        <!-- Image handling -->
        <div v-if="quiz.questions[currentQuestionIndex].image">
          <img
            :src="`data:image/jpeg;base64,${quiz.questions[currentQuestionIndex].image}`"
            alt="Question Image"
          />
          <button @click="removeImage(quiz.questions[currentQuestionIndex])">
            Remove Image
          </button>
        </div>
        <div v-else>
          <input
            type="file"
            @change="
              (event) =>
                handleFileChange(event, quiz.questions[currentQuestionIndex])
            "
          />
        </div>
        <button @click="removeQuestion">Remove Question</button>
        <button @click="goToPreviousQuestion">Previous</button>
        <button @click="goToNextQuestion">Next</button>
        <button @click="addQuestion">Add New Question</button>
      </div>
    </div>

    <button @click="submit">Submit</button>
    
    <button @click="exportQuizToCSV"> Export quiz </button>
  </div>
</template>
<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

input, select, button {
  padding: 0.5rem;
  border-radius: 5px;
  border: 1px solid #cacaca;
}

button {
  background-color: #4caf50;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.custum-file-upload {
  height: 200px;
  width: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 2px dashed #cacaca;
  background-color: rgba(255, 255, 255, 1);
  padding: 1.5rem;
  border-radius: 10px;
  box-shadow: 0px 48px 35px -48px rgba(0,0,0,0.1);
}

.custum-file-upload .icon {
  display: flex;
  align-items: center;
  justify-content: center;
}

.custum-file-upload .icon svg {
  height: 80px;
  fill: rgba(75, 85, 99, 1);
}

.custum-file-upload .text {
  display: flex;
  align-items: center;
  justify-content: center;
}

.custum-file-upload .text span {
  font-weight: 400;
  color: rgba(75, 85, 99, 1);
}

.custum-file-upload input[type="file"] {
  display: none;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 0.5rem 0;
}

img {
  max-width: 100%;
  height: auto;
  border-radius: 5px;
}
</style>
