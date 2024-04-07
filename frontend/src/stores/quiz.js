import { defineStore } from "pinia";

export const useQuizStore = defineStore("quiz", {
  state: () => ({
    quizes: [],
    categories: [],
    userAnswers: [],
  }),
  getters: {
    getCategories() {
      return this.categories;
    },
    getQuizes() {
      return this.quizes;
    },
    getQuiz(quizId) {
      return this.quizes.find((quiz) => quiz.id === quizId);
    },

    getUserAnswers() {
      return this.userAnswers;
    },
  },
  actions: {
    setQuizes(quizes) {
      this.quizes = quizes;
    },
    setCategories(categories) {
      this.categories = categories;
    },
    resetUserAnswers() {
      this.userAnswers = [];
    },
    setQuestions(questions) {
      this.questions = questions;
      console.log(questions.length);
      this.length = questions.length;
    },
  },
});
