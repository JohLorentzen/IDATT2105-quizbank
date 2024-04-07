<script setup>
import {computed, ref} from "vue";
import { useQuizesStore } from "@/stores/quizes";
import Papa from "papaparse";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import router from "@/router/index.js";


const props = defineProps({
  quiz: Object,
});

const currentQuestionIndex = ref(0);
const emit = defineEmits(["submit"]);
const quiz = ref(props.quiz);
const newAlternative = ref("");
const quizesStore = useQuizesStore();
const categories = useQuizesStore().getCategories;

const questionNumberTitle = computed(() => {
  const totalNumberOfQuestions = quiz.value.questions.length;
  if (totalNumberOfQuestions === 0) {
    return "No questions added yet.."
  }
  return `Question no. ${currentQuestionIndex.value + 1} / ${totalNumberOfQuestions}`
})

const submit = () => {
  emit("submit", quiz.value);
};

const addAlternative = () => {
  if (newAlternative.value.trim()) {
    quiz.value.questions[currentQuestionIndex.value].choices.push(
      newAlternative.value
    );
    newAlternative.value = "";
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
  if (currentQuestionIndex.value < quiz.value.questions.length - 1) {
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
    problem: "",
    solution: "",
    type: "FILL_IN_THE_BLANKS",
    choices: [],
    tags: [],
    image: null,
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
  const quizData = quiz.value.questions.map((q) => ({
    Problem: q.problem,
    Solution: q.solution,
    Choices: formatChoices(q),
  }));

  const csv = Papa.unparse({
    fields: ["Problem", "Solution", "Choices"],
    data: quizData,
  });

  const blob = new Blob([csv], { type: "text/csv;charset=utf-8;" });
  const link = document.createElement("a");
  const url = URL.createObjectURL(blob);
  link.setAttribute("href", url);
  link.setAttribute(
    "download",
    `${quiz.value.quizName.replace(/\s+/g, "_")}.csv`
  );
  link.style.visibility = "hidden";
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

const formatChoices = (question) => {
  if (question.type === "TRUE_FALSE") {
    return "True, False";
  }
  return question.choices.join(", ");
};
</script>
<template>
  <div class="component-container">
      <div class="quiz-container">
        <h2>Quiz</h2>
      <div class="label-input-div">
        <label>Quiz Name</label>
        <input type="text" v-model="quiz.quizName" />
      </div>
      <div class="label-input-div">
        <label>Category</label>
        <select v-model="quiz.category">
          <option v-for="category in categories" :value="category">{{ category }}</option>
        </select>
      </div>
      <div class="label-input-div">
        <label>Difficulty</label>
        <select v-model="quiz.difficultyLevel">
          <option value="EASY">Easy</option>
          <option value="MEDIUM">Medium</option>
          <option value="HARD">Hard</option>
        </select>
      </div>
      <p>Questions: {{ quiz.questions.length }}</p>
    </div>
    <div class="question-container">
      <div class="navigate-questions-container">
        <FontAwesomeIcon icon="chevron-left" class="navigation fa-2x" @click="goToPreviousQuestion"/>
        <h2>{{ questionNumberTitle }}</h2>
        <FontAwesomeIcon icon="chevron-right" class="navigation fa-2x" @click="goToNextQuestion"/>
      </div>
      <div class="question-fields" v-if="quiz.questions[currentQuestionIndex]">
        <div class="label-input-div">
          <label>Question</label>
          <input
              type="text"
              v-model="quiz.questions[currentQuestionIndex].problem"
              placeholder="New question"
          />
        </div>
        <div class="label-input-div">
          <p>Question type</p>
          <select v-model="quiz.questions[currentQuestionIndex].type">
            <option value="FILL_IN_THE_BLANKS">Fill in the blanks</option>
            <option value="MULTIPLE_CHOICE">Multiple choice</option>
            <option value="TRUE_FALSE">True or False</option>
          </select>
        </div>
        <div class="label-input-div">
          <label>Answer</label>
          <input
              type="text"
              v-model="quiz.questions[currentQuestionIndex].solution"
              placeholder="New solution"
          />
        </div>
        <p v-if="quiz.questions[currentQuestionIndex].type === 'MULTIPLE_CHOICE'" class="alternatives-title">Add alternatives</p>
        <div v-if="quiz.questions[currentQuestionIndex].type === 'MULTIPLE_CHOICE'" class="add-alternative-container">
          <input type="text" id="alternativeInput" v-model="newAlternative" placeholder="type alternative.."/>
          <button type="button" @click="addAlternative()">
            Add alternative
          </button>
        </div>
        <div v-if="quiz.questions[currentQuestionIndex].type === 'MULTIPLE_CHOICE'">
          <p class="alternative-list-title">Alternatives (click to remove)</p>
          <ul>
            <div class="alternative" v-for="(choice, index) in quiz.questions[currentQuestionIndex].choices" :key="index" @click="removeAlternative(index)">
              <li>{{ choice }}</li>
              <FontAwesomeIcon icon="xmark" />
            </div>
          </ul>
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
      </div>
      <div class="button-container">
        <button v-if="quiz.questions.length > 0" class="remove-q-btn" @click="removeQuestion">Remove Question</button>
        <button class="add-q-btn" @click="addQuestion">Add New Question</button>
      </div>
      </div>
      <div class="save-changes-container">
        <button class="save-changes-btn" @click="submit">Save changes</button>
        <button class="export-btn" @click="exportQuizToCSV">Export quiz</button>
      </div>
    </div>
</template>
<style scoped>
.component-container {
  grid-column: 2 / -2;
  margin: 1em auto 0;
  display: flex;
  gap: 2em;
  flex-wrap: wrap;
  flex-direction: column;
}

.quiz-container, .question-container {
  padding: 1em;
  background-color: var(--bg-light-gray);
  display: flex;
  flex-direction: column;
  border-radius: 1em;
  max-width: 600px;
  gap: 0.4em;
  flex-grow: 1;
}

h2 {
  font-weight: bold;
  font-size: 2rem;
}

.label-input-div {
  display: flex;
  flex-direction: column;
  gap: 0.2em;
}

input, select {
  font-size: 1rem;
  padding: 0.4em;
}

.navigate-questions-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navigation {
  cursor: pointer;
}

.add-alternative-container {
  display: flex;
  align-items: center;
}

.alternatives-title {
  font-weight: bold;
  margin-top: 0.4em;
}

#alternativeInput {
  flex-grow: 2;
  margin: 0;
}

.add-alternative-container button {
  padding: 0.74em 0.4em;
  border: none;
  background-color: var(--bg-very-light-blue-shadow);
  color: var(--text-color);
  font-weight: bolder;
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

.alternative-list-title {
  margin: 1em 0 0.3em;
  color: var(--text-color-grey)
}

.question-fields {
  display: flex;
  flex-direction: column;
  gap: 0.4em;
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
  box-shadow: 0px 48px 35px -48px rgba(0, 0, 0, 0.1);
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

img {
  max-width: 100%;
  height: auto;
  border-radius: 5px;
}

.button-container {
  display: flex;
  gap: 1em;
  justify-content: center;
}

button {
  padding: 0.8em 1.6em;
  border: none;
  cursor: pointer;
  border-radius: 0.4em;
}

.remove-q-btn {
  border: 2px solid #ffa69e;
  background: none;
  font-weight: bold;
  color: var(--text-color-grey);
}

.remove-q-btn:hover {
  background: #ffa69e;
}

.add-q-btn, .save-changes-btn {
  background: var(--button-bg-strong-blue);
  color: white;
  font-weight: bold;
}

.add-q-btn:hover, .save-changes-btn:hover {
  background: var(--button-bg-hover-blue);
}

.save-changes-container {
  display: flex;
  max-width: 600px;
  gap: 1em;
  justify-content: center;
}

.save-changes-container button {
  padding: 1em 2em;
}

.save-changes-btn {
  text-transform: uppercase;
}

.export-btn {
  text-transform: uppercase;
  font-weight: bold;
  background-color: var(--bg-light-gray);
  color: var(--button-bg-strong-blue)
}

.export-btn:hover {
  background: var(--bg-light-gray-shadow);
  color: var(--text-color);
}
</style>
