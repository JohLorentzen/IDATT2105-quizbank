import { defineStore } from 'pinia';

export const useQuizesStore = defineStore('quizes', {
    state: () => ({
        quizes: Array,
        currentQuiz: Number,
        categories: [],
    }),
    getters: {

        getCategories() {
            return this.categories;
        },
        getQuizes() {
            return this.quizes;
        },
        getQuiz(quizId) {
            return this.quizes.find(quiz => quiz.id === quizId);
        }, 
    },
    actions: {
        setQuizes(quizes) {
            this.quizes = quizes;
        },
        setCategories(categories) {
            this.categories = categories;
        },
    },
});
