import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useQuizStore = defineStore('quiz', () => {
  const quiz = ref([])

  function getQuestions() {
    return quiz.value
  }

  const totalQuestions = computed(() => quiz.value.length)

  return { quiz, getQuestions, totalQuestions}
})