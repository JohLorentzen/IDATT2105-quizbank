<script setup>
import {computed, defineEmits, onMounted, ref, watch} from "vue";
import {useQuizesStore} from "@/stores/quizes";
import axios from 'axios';
import {vOnClickOutside} from '@vueuse/components'

const emit = defineEmits(['updateFilters', 'updateDifficultyFilter', 'updateSharedStatusFilter']);

const quizesStore = useQuizesStore();
const searchTerm = ref("");
const filters = ref({categories: [], tags: []});
const matchingFilters = ref([]);
const chosenFilters = ref([]);
const showResultContainer = ref(false);

const matchingFilterMessage = computed(() => {
  const numberOfMatches = matchingFilters.value.length;
  let message;
  if (numberOfMatches > 0) {
    message = "Available filters";
  } else {
    message = "No matching filters";
  }
  return message;
})

const fetchFilters = async () => {
  try {
    if (!quizesStore.getCategories.length > 0) {
      const {data: categories} = await axios.get('http://localhost:8080/rest/quiz/categories', {
        headers: {Authorization: `Bearer ${localStorage.getItem('token')}`}
      });
      quizesStore.setCategories(categories);
    }

    const {data: tags} = await axios.get('http://localhost:8080/rest/quiz/tags', {
      headers: {Authorization: `Bearer ${localStorage.getItem('token')}`}
    });

    filters.value = {
      categories: quizesStore.getCategories,
      tags
    };
  } catch (error) {
    console.error("Error fetching filters: " + error);
  }
};

onMounted(fetchFilters);

watch(searchTerm, newValue => {
  setMatchingFilters()
});

function setMatchingFilters() {
  matchingFilters.value = [...filters.value.categories, ...filters.value.tags]
      .filter(filter => filter.toLowerCase().includes(searchTerm.value.toLowerCase()) && !chosenFilters.value.includes(filter)).slice(0, 10);
}

function chooseFilter(filter) {
  if (!chosenFilters.value.includes(filter)) {
    chosenFilters.value.push(filter);
  }
  searchTerm.value = "";
  setMatchingFilters();
  emit("updateFilters", chosenFilters.value);
}

function removeFilter(filter) {
  const index = chosenFilters.value.indexOf(filter);
  if (index > -1) {
    chosenFilters.value.splice(index, 1);
    emit("updateFilters", chosenFilters.value);
  }
}

function showSuggestions() {
  if (searchTerm.value === '' && showResultContainer.value === false) {
    setMatchingFilters();
  }
  showResultContainer.value = true;
}

function hideSuggestions() {
  showResultContainer.value = false;
}

const selectedDifficulty = ref('');

function filterDifficulty() {
  emit('updateDifficultyFilter', selectedDifficulty.value);
}

const selectedSharedStatus = ref(false);

function filterSharedStatus() {
  emit('updateSharedStatusFilter', selectedSharedStatus.value);
}
</script>


<template>
  <div class="component-container">
    <div class="search-container" v-on-click-outside="hideSuggestions">
      <div class="row">
        <input
            type="text"
            placeholder="Search for categories or tags..."
            autocomplete="off"
            v-model="searchTerm"
            @focus="showSuggestions"
        />
      </div>
      <div class="search-results" v-if="showResultContainer">
        <p>{{ matchingFilterMessage }}</p>
        <ul>
          <li v-for="filter in matchingFilters" :key="filter" @click="chooseFilter(filter)">
            {{ filter }}
          </li>
        </ul>
      </div>
    </div>
    <div class="chosen-filters-container" v-if="chosenFilters.length > 0">
      <p>Chosen Filters</p>
      <ul>
        <li v-for="filter in chosenFilters" :key="filter" @click="removeFilter(filter)">
          {{ filter }}
        </li>
      </ul>
    </div>
    <div class="custom-select-container">
      <label for="difficultyFilter">Filter by difficulty</label>
      <select class="custom-select" id="difficultySelect" v-model="selectedDifficulty" @change="filterDifficulty">
        <option value="">All</option>
        <option value="easy">Easy</option>
        <option value="medium">Medium</option>
        <option value="hard">Hard</option>
      </select>
    </div>
    <div class="custom-checkbox-container">
      <input type="checkbox" id="sharedFilter" v-model="selectedSharedStatus" @change="filterSharedStatus"
             class="share-check-box">
      <label for="sharedFilter">Only show quizzes shared with me</label>
    </div>
  </div>
</template>


<style scoped>
.component-container {
  display: grid;
  grid-column: 2 / -2;
  grid-template-rows: 50px 80px;
  height: 140px;
}

.search-container, .chosen-filters-container {
  grid-column: 2 / -2;
  background-color: white;
}

.search-container {
  font-size: 16px;
  background: var(--bg-very-light-blue-shadow);
  grid-row: 1 / 3;
}

.chosen-filters-container {
  grid-row: 2 / 3;
  align-self: center;
}

.row {
  display: flex;
  align-items: center;
  border: 1px solid var(--input-field-border);
  border-radius: 0.1em;
}

.row input {
  flex-grow: 2;
  font-size: 1rem;
  font-family: sans-serif;
  padding: 0.8em 1em;
  border: none;
}

.search-results {
  border: 1px solid #cccccc;
  padding: 1em;
  border-radius: 10px;
  box-shadow: 0 1px 15px rgba(0, 0, 0, 0.2);
  background: white;
  position: relative;
  z-index: 10;
}

.search-results ul, .chosen-filters-container ul {
  display: flex;
  width: 100%;
  gap: 1em;
  list-style: none;
  flex-wrap: wrap;
  overflow: auto;
  margin-top: 0.6em;
}

.search-results p {
  font-weight: bold;
}

.search-results li {
  font-size: 0.8rem;
  background: #eeeeee;
  padding: 0.3em 0.8em;
  border-radius: 2em;
}

.search-results li:hover {
  cursor: pointer;
  background: #dddddd;
}

.chosen-filters-container {
  font-size: 12px;
  padding: 1em 0;
  background-color: var(--bg-very-light-blue-shadow);
}

.chosen-filters--container p {
  color: var(--text-color);
}

.chosen-filters--container ul {
  margin-top: 1em;
}

.chosen-filters-container li {
  padding: 0.5em 1em;
  border-radius: 2em;
  border: 1px solid var(--text-color-light-grey);
  width: fit-content;
}

.chosen-filters-container li:hover {
  background: var(--text-color-light-grey);
  color: var(--text-color-white);
  cursor: pointer;
}

.custom-select-container {
  width: 100%;
  max-width: 300px; /* Adjust based on your layout needs */
  margin: 0 auto;
  position: relative;
}

.custom-select {
  display: block;
  width: 100%;
  padding: 0.6em 1.4em 0.5em 0.8em;
  font-size: 1rem;
  border-radius: 0.25rem;
  border: 1px solid #ccc;
  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
  background-color: white;
  background-repeat: no-repeat, repeat;
  background-position: right 0.7em top 50%, 0 0;
  background-size: 0.65em auto, 100%;
}

.custom-select:focus {
  border-color: #aaa;
  outline: none;
}

.custom-select-container:focus-within .custom-select {
  border-color: #aaa;
}

.custom-checkbox-container {
  display: flex;
  align-items: center;
}

.custom-checkbox-container label {
  margin-left: 10px;
}

.share-check-box {
  width: 20px;
  height: 20px;
  cursor: pointer;
  align-self: center;
}

.share-check-box:checked {
  background-color: #2196F3;
}

@media (min-width: 720px) {
  .search-container {
    font-size: 18px;
  }

  .search-results li {
    font-size: 1rem;
  }

  .chosen-filters-container {
    font-size: 15px
  }
}
</style>