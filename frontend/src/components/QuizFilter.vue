<script setup>
import { onMounted, ref, watch } from "vue";
import { useQuizesStore } from "@/stores/quizes";
import axios from 'axios';

const emit = defineEmits(['updateFilters']);

const quizesStore = useQuizesStore();
const searchTerm = ref("");
const filters = ref({ categories: [], tags: [] });
const matchingFilters = ref([]);
const chosenFilters = ref([]);

const fetchFilters = async () => {
  try {
    if (!quizesStore.getCategories.length > 0) {
      const {data: categories} = await axios.get('http://localhost:8080/rest/quiz/categories', {
        headers: {Authorization: `Bearer ${localStorage.getItem('token')}`}
      });
      quizesStore.setCategories(categories);
    }

    const { data: tags } = await axios.get('http://localhost:8080/rest/quiz/tags', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
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
  matchingFilters.value = [...filters.value.categories, ...filters.value.tags]
      .filter(filter => filter.toLowerCase().includes(newValue.toLowerCase()) && !chosenFilters.value.includes(filter));
});

function chooseFilter(filter) {
  if (!chosenFilters.value.includes(filter)) {
    chosenFilters.value.push(filter);
  }
  searchTerm.value = "";
  emit("updateFilters", chosenFilters.value);
}

function removeFilter(filter) {
  const index = chosenFilters.value.indexOf(filter);
  if (index > -1) {
    chosenFilters.value.splice(index, 1);
    emit("updateFilters", chosenFilters.value);
  }
}
</script>


<template>
  <div class="search-container">
    <div class="row">
      <input
          type="text"
          placeholder="Search for categories or tags..."
          autocomplete="off"
          v-model="searchTerm"
      />
    </div>
    <div class="search-results" v-if="searchTerm">
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
</template>


<style scoped>
.search-container, .chosen-filters-container {
  grid-column: 2 / -2;
  background-color: white;
}

.search-container {
  font-size: 16px;
}

.row {
  padding: 0.1em;
  display: flex;
  align-items: center;
  border: 1px solid #cccccc;
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