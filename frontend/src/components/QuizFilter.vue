<script setup>
import {onMounted, ref, watch} from "vue";

const props = defineProps({
  quizes: Array
})

const categories = ref([]);
const searchTerm = ref("");
const foundCategories = ref([])

watch( // have to use watcher because quizes depends on async call
    () => props.quizes,
    (newValue) => {
  getCategories();
}, {immediate: true})

watch(searchTerm, (newValue) => {
  foundCategories.value = [];
  for (const category of categories.value) {
    if (category.includes(newValue)) foundCategories.value.push(category);
  }
})

function getCategories() {
  for (const quiz of props.quizes) {
    if (categories.value.includes(quiz.category)) continue
    categories.value.push(quiz.category)
  }
  console.log("Categories: " + categories.value)
}
</script>

<template>
  <div class="search-container">
    <div class="row">
      <input type="text" placeholder="Search for filter.." autocomplete="off" v-model="searchTerm">
      <button>Clear Filters</button>
    </div>
    <div class="search-results">
      <ul>
        <li v-for="category in foundCategories" :key="category">{{category}}</li>
      </ul>
    </div>
  </div>
  <h1>Quiz Filter</h1>
</template>

<style scoped>
.search-container {
  background: #ffffff;
  font-size: 16px;
}

.row,
.row input,
.row button,
.search-results,
.search-results ul,
.search-results li{
  margin: 0;
  padding: 0;
}

.row {
  display: flex;
}

.row input {
  flex-grow: 2;
  font-size: 1rem;
  font-family: sans-serif;
  padding: 0.2rem 0.4rem;
}

.row button {
  flex-grow: 1;
  font-size: 1rem;
}

.search-results ul {
  list-style: none;
}

.search-results li:hover {
  cursor: pointer;
  background: #eeeeee;
}

</style>