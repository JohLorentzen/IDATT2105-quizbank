import { defineStore } from 'pinia';

export const useQuizesStore = defineStore('quizes', {
    state: () => ({
        quizes: [],
        currentQuiz: 0,

    }),
    getters: {
        
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
        }
    },
});
