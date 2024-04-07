<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useQuizesStore } from "@/stores/quizes";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

const userStore = useUserStore();

const quiz = ref({                                          
  quizId: null,
  quizName: "",
  createdByUserId: null,
  difficultyLevel: "",
  category: "",
  questions: [],
});
const question = ref({
  questionId: null,
  problem: "",
  solution: "",
  type: "",
  choices: [],
  image: null,

});
const categories = ref([]);
const quizesStore = useQuizesStore();

const alternativesText = computed(() => {
  if (question.value.choices.length > 0) {
    return "Answer Alternatives (click to delete)";
  }
  return "No alternatives added yet.."
})

const fetchCategories = () => {
    if (quizesStore.getCategories) {
      categories.value = quizesStore.categories;
      return;
    }
    axios.get('http://localhost:8080/rest/quiz/categories', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    }).then(response => {
      quizesStore.setCategories(response.data);
      categories.value = response.data;
    }, error => {

      console.log("This is axios error " + error);
    })
};
const MAX_IMAGE_SIZE = 5000000; // e.g., 5MB
const VALID_IMAGE_TYPES = ['image/jpeg', 'image/png', 'image/gif'];

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  if (!VALID_IMAGE_TYPES.includes(file.type)) {
    alert('Invalid file type. Only JPEG, PNG and GIF are allowed.');
    return;
  }

  if (file.size > MAX_IMAGE_SIZE) {
    alert(`File is too large. Maximum size is ${MAX_IMAGE_SIZE / 1000000} MB.`);
    return;
  }

  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => {
  const base64String = reader.result.split(',')[1];
  question.value.image = base64String;
  } ;
  reader.onerror = (error) => {
    console.error('Error reading file:', error);
  };
};


const isQuestionValid = computed(() => {
  return question.value.problem && question.value.solution && question.value.type &&
         (question.value.type !== 'MULTIPLE_CHOICE' || question.value.choices.length > 0);
});
const isQuizValid = computed(() => {
  return quiz.value.quizName && quiz.value.difficultyLevel && quiz.value.category;
});
const alternativeInput = ref("");
const emit = defineEmits(["submitQuiz"]);
const submit = () => {
  if (!isQuizValid.value) {
    alert("Please fill all quiz fields and add at least one question.");
    return;
  }
  emit("submitQuiz", quiz.value);
};
const quizView = ref(true);

const buttonLabel = computed(() =>
  quizView.value ? "Add question" : "Quiz details"
);

const changeView = () => {
  quizView.value = !quizView.value;
};

watch(() => question.value.type, (newType) => {
  if (newType === 'TRUE_FALSE') {
    
    question.value.solution = 'TRUE'; // or 'FALSE', depending on your preference
  }
});

const saveQuestion = (event) => {
  event.preventDefault();
  if (!isQuestionValid.value) {
    alert("Please fill all question fields and add at least one alternative for multiple choice questions.");
    return;
  }
  question.value.choices.push(question.value.solution);
  if (!question.value.choices.includes(question.value.solution)) {
    question.value.choices.push(question.value.solution);
  }
  quiz.value.questions.push(question.value);
  question.value = {
    questionId: null,
    problem: "",
    solution: "",
    type: "",
    choices: [],
  };
  alternativeInput.value = ""; // Refresh the input field
};
const addAlternative = (alternative) => {
  if (!alternative) {
    alert("Alternative cannot be empty.");
    return;
  }
  question.value.choices.push(alternative);
  alternativeInput.value = ""; // Refresh the input field
};

function removeAlternative(index) {
  question.value.choices.splice(index, 1);
}

onMounted(fetchCategories);
</script>

<template>
  <div v-if="quizView" class="container">
    <form>
      <label for="quizName">Quiz Name</label>
      <input type="text" id="quizName" v-model="quiz.quizName" />
      <label for="quizDifficulty">Quiz Difficulty</label>
      <select id="quizDifficulty" v-model="quiz.difficultyLevel">
        <option value="" disabled selected>Select difficulty</option>
        <option value="EASY">Easy</option>
        <option value="MEDIUM">Medium</option>
        <option value="HARD">Hard</option>
      </select>
      <label for="quizCategory">Quiz Category</label>
      <select id="quizCategory" v-model="quiz.category">
        <option value="" disabled selected>Select category</option>
        <option v-for="category in categories" :value="category">{{ category }}</option>
      </select>
    </form>
  </div>
  <div v-else class="container">
    <form>
      <label class="custom-file-upload" for="file">
        <div class="icon">
        <svg xmlns="http://www.w3.org/2000/svg" fill="" viewBox="0 0 24 24"><g stroke-width="0" id="SVGRepo_bgCarrier"></g><g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g><g id="SVGRepo_iconCarrier"> <path fill="" d="M10 1C9.73478 1 9.48043 1.10536 9.29289 1.29289L3.29289 7.29289C3.10536 7.48043 3 7.73478 3 8V20C3 21.6569 4.34315 23 6 23H7C7.55228 23 8 22.5523 8 22C8 21.4477 7.55228 21 7 21H6C5.44772 21 5 20.5523 5 20V9H10C10.5523 9 11 8.55228 11 8V3H18C18.5523 3 19 3.44772 19 4V9C19 9.55228 19.4477 10 20 10C20.5523 10 21 9.55228 21 9V4C21 2.34315 19.6569 1 18 1H10ZM9 7H6.41421L9 4.41421V7ZM14 15.5C14 14.1193 15.1193 13 16.5 13C17.8807 13 19 14.1193 19 15.5V16V17H20C21.1046 17 22 17.8954 22 19C22 20.1046 21.1046 21 20 21H13C11.8954 21 11 20.1046 11 19C11 17.8954 11.8954 17 13 17H14V16V15.5ZM16.5 11C14.142 11 12.2076 12.8136 12.0156 15.122C10.2825 15.5606 9 17.1305 9 19C9 21.2091 10.7909 23 13 23H20C22.2091 23 24 21.2091 24 19C24 17.1305 22.7175 15.5606 20.9844 15.122C20.7924 12.8136 18.858 11 16.5 11Z" clip-rule="evenodd" fill-rule="evenodd"></path> </g></svg>
        </div>
        <div class="text">
          <span>Click to upload image</span>
        </div>
        <input type="file" id="file" @change="handleFileChange">
      </label>
      <label for="questionProblem">Question</label>
      <input type="text" id="questionProblem" v-model="question.problem" />
      <label for="questionType">Question Type</label>
      <select v-model="question.type" >
        <option value="" disabled selected>Select question type</option>
        <option value="MULTIPLE_CHOICE">Multiple Choice</option>
        <option value="FILL_IN_THE_BLANKS">Fill in the blank</option>
        <option value="TRUE_FALSE">True or False</option>
      </select>
      <label for="questionSolution" v-if="question.type !== 'TRUE_FALSE'">Answer</label>
       <input type="text" id="questionSolution" v-model="question.solution" v-if="question.type !== 'TRUE_FALSE'"/>
      <div v-if="question.type === 'MULTIPLE_CHOICE'" class="multiple-choice-container">
        <p class="alternatives-title">Add alternatives</p>
        <div class="add-alternative-container">
          <input type="text" id="alternativeInput" v-model="alternativeInput" placeholder="type alternative.."/>
          <button type="button" @click="addAlternative(alternativeInput)">
            Add alternative
          </button>
        </div>
        <p class="alternative-list-title">{{ alternativesText }}</p>
        <ul>
          <div class="alternative" v-for="(answer, index) in question.choices" :key="index" @click="removeAlternative(index)">
            <li >
              {{ answer }}
            </li>
            <FontAwesomeIcon icon="xmark" />
          </div>
        </ul>
      </div>
      <div v-else-if="question.type === 'TRUE_FALSE'">
        <div class="true-false-option">
          <label for="trueOption">True</label>
          <input type="radio" id="trueOption" value="TRUE" v-model="question.solution">
        </div>
        <div class="true-false-option">
          <label for="falseOption">False</label>
          <input type="radio" id="falseOption" value="FALSE" v-model="question.solution">
        </div>
      </div>
      <button @click="saveQuestion" :disabled="!isQuestionValid" class="save-button">Save question</button>
    </form>
  </div>
  <div class="button-container">
    <button @click="changeView">{{ buttonLabel }}</button>
    <button @click="submit" type="submit" :disabled="!isQuizValid">Save quiz</button>
  </div>
</template>
<style scoped>
.container {
  background-color: var(--bg-light-gray);
  padding: 1.5em 1.5em 1.5em;
  border-radius: 1em;
  max-width: 600px;
  margin-top: 1em;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 1.1rem;
  color: var(--text-color-grey);
}

form input, select {
  font-size: 1.125rem;
  margin-bottom: 1em;
  padding: 0.2em;
  margin-top: 0.1em;
}

.button-container {
  display: flex;
  margin-top: 1.5em;
  gap: 1.5em;
}

.button-container button {
  border: none;
  padding: 0.7em 1.4em;
  background: var(--button-bg-strong-blue);
  font-weight: bold;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  border-radius: 0.5em;
}

button:hover {
  background: var(--button-bg-hover-blue);
}

button:active {
  background: var(--button-bg-active-blue);
}

button:disabled {
  background-color: var(--color-background-mute);
  color: var(--text-color-grey)
}
.custom-file-upload {
  display: flex;
  flex-direction: column;
  gap: 1em;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  border: 2px dashed #cacaca;
  background-color: var(--bg-white);
  padding: 1rem;
  border-radius: 0.8em;
  margin-bottom: 1em;
}

.custom-file-upload .icon {
  display: flex;
  align-items: center;
  justify-content: center;
}

.custom-file-upload .icon svg {
  height: 3em;
  fill: rgba(75, 85, 99, 1);
}

.custom-file-upload .text {
  display: flex;
  align-items: center;
  justify-content: center;
}

.custom-file-upload .text span {
  font-weight: 400;
  color: rgba(75, 85, 99, 1);
}

.custom-file-upload input {
  display: none;
}

.save-button {
  margin-top: 2em;
  width: 100%;
  padding: 1em;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 0.5em;
  background: var(--button-bg-strong-blue);
  color: var(--text-color-white)
}

.add-alternative-container {
  display: flex;
  align-items: center;
}

.alternatives-title {
  font-weight: bold;
  margin-bottom: 0.3em;
}

#alternativeInput {
  flex-grow: 2;
  margin: 0;
}

.add-alternative-container button {
  padding: 0.6em 0.4em;
  border: none;
  background-color: var(--bg-very-light-blue-shadow);
  color: var(--text-color);
  font-weight: bolder;
}

.alternative-list-title {
  margin: 1em 0;
  color: var(--text-color-grey)
}

.alternative {
  margin: 0.1em;
  font-weight: bolder;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.3em;
  padding: 0.2em;
}

.alternative:hover {
  background: #ffa69e;
  cursor: pointer;
}

.true-false-option {
  display: flex;
  align-items: center;
  font-size: 1.2rem;
  gap: 0.5em;
  font-weight: bold;
}

.true-false-option input {
  padding: 0;
  margin: 0;
}
</style>