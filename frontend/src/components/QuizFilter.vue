<script setup>
import {computed, onMounted, ref, watch} from "vue";
import { vOnClickOutside } from '@vueuse/components'
import axios from 'axios';

const emit = defineEmits(['chosenCategories'])

const showCategorySuggestions = ref(false);
const searchTerm = ref("");
const categories = ref([]);
const matchingCategories = ref([]);
const chosenCategories = ref([]);


const fetchCategories = () => {
    axios.get('http://localhost:8080/rest/quiz/categories', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    }).then(response => {
      categories.value = response.data;
    }, error => {

      console.log("This is axios error " + error);
    })
};

onMounted(fetchCategories);

const matchingFilterMessage = computed(() => {
  const numberOfMatches = matchingCategories.value.length;
  let message;
  if (numberOfMatches > 0) {
    message = "Available filters";
  } else if (chosenCategories.value.length === categories.value.length) {
    message = "No more categories to choose from";
  } else {
    message = "No matching filters";
  }
  return message;
})


// TODO: research usage of watch vs composed
// TODO: edit search to look for all cases
watch(searchTerm, (newValue) => {
  getMatchingCategories(newValue);
})

watch(chosenCategories.value, () => {
  emit("chosenCategories", chosenCategories.value);
})

function getMatchingCategories(searchTerm) {
  matchingCategories.value = [];
  for (const category of categories.value) {
    if (category.toLowerCase().includes(searchTerm.toLowerCase()) && !chosenCategories.value.includes(category)) {
      matchingCategories.value.push(category);
    }
  }
}

function chooseCategory(category) {
  chosenCategories.value.push(category);
  searchTerm.value = "";
  getMatchingCategories(searchTerm.value)
}

function removeCategory(category) {
  const removeAtIndex = chosenCategories.value.indexOf(category);
  chosenCategories.value.splice(removeAtIndex, 1);
}

function hideSuggestions() {
  showCategorySuggestions.value = false;
}

function showSuggestions() {
  getMatchingCategories(searchTerm.value)
  showCategorySuggestions.value = true;
}

// TODO: make component for category list items
</script>

<template>
  <div class="search-container" v-on-click-outside="hideSuggestions">
    <div class="row">
      <input
          type="text"
          placeholder="Search for filter.."
          autocomplete="off"
          v-model="searchTerm"
          @focus="showSuggestions"
      >
    </div>
    <div class="search-results" v-if="showCategorySuggestions" >
      <p>{{ matchingFilterMessage }}</p>
      <ul v-if="matchingCategories.length > 0">
        <li
            v-for="category in matchingCategories"
            :key="category"
            @click="chooseCategory(category)"
        >{{category}}
        </li>
      </ul>
    </div>
  </div>
  <div class="chosen-category-container" v-if="chosenCategories.length > 0">
    <p>Chosen categories</p>
    <ul>
      <li
          v-for="category in chosenCategories"
          :key="category"
          @click="removeCategory(category)"
      >{{category}}</li>
    </ul>
  </div>
</template>

<style scoped>
div, ul, li, input, button, p {
  margin: 0;
  padding: 0;
}

.search-container, .chosen-category-container {
  width: 400px;
}

.search-container {
  background: #ffffff;
  font-size: 16px;
}

.row {
  padding: 0.1em;
  display: flex;
  align-items: center;
  border: 1px solid #cccccc;
  border-radius: 5px;
}

.row input {
  flex-grow: 2;
  font-size: 1rem;
  font-family: sans-serif;
  padding: 0.8rem 1.6rem;
  border: none;
}

.search-results {
  border: 1px solid #cccccc;
  padding: 1em;
  border-radius: 10px;
  box-shadow: 0 1px 15px rgba(0,0,0,0.2);
}

.search-results ul, .chosen-category-container ul {
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

.chosen-category-container {
  font-size: 12px;
  padding: 1em;
}

.chosen-category-container p {
  color: #777777;
}

.chosen-category-container li {
  padding: 0.5em 1em;
  background: #eeeeee;
  border-radius: 0.2em;
  width: fit-content;
}

.chosen-category-container li:hover {
  background: #dddddd;
  cursor: pointer;
}
</style>