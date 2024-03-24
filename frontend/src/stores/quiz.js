import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useQuizStore = defineStore('counter', () => {
  const quiz = ref([])

  function getQuestions() {
    return quiz.value
  }

  return { quiz, getQuestions }
})