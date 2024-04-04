import { defineStore } from 'pinia'

export const useQuizStore = defineStore('quiz', {
  state: () => ({
    questions: [],
    length: 0,
    currentQuestion: 0,
    correctAnswers: 0,
    userAnswers: [],
  }),
  getters: {
    getQuestions() {
      return this.questions;
    },
    getLength() {
      return () => {
        return this.length;
      };
    },
    getQuestion() {
      return () => {
        const question = this.questions[this.currentQuestion];
        this.currentQuestion++;
        return question;
      };
    },
    getUserAnswers() {
      return this.userAnswers;
    },
  },
  actions: {
    
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
