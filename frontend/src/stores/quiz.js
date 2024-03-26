import { defineStore } from 'pinia'

export const useQuizStore = defineStore('quiz', {
  state: () => ({
    questions: [],
    length: 0,
    currentQuestion: 0,
    correctAnswers: 0,
  }),
  getters: {
    getQuestions() {
      return this.questions;
    },
    getLength() {
      return () => {
        return this.length;
      }
    },
    getQuestion() {
      return () => {
        const question = this.questions[this.currentQuestion];
        this.currentQuestion++;
        return question;
      };
    },
  },
  actions: {
    setQuestions(questions) {
      this.questions = questions;
      console.log(questions.length);
      this.length = questions.length;
    },
  },
});
