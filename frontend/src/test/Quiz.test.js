import {describe, expect, it} from 'vitest';
import {createApp} from 'vue';
import {createPinia} from 'pinia';
import {mount} from '@vue/test-utils';
import Quiz from '@/components/Quiz.vue';

describe('Quiz', () => {
    const app = createApp({});
    app.use(createPinia());

    it('renders quiz name correctly', () => {
        const wrapper = mount(Quiz, {
            props: {
                selectedQuiz: {
                    quizName: 'Test Quiz',
                    questions: [],
                },
            },
            global: {
                plugins: [app],
            },
        });

        const quizHeader = wrapper.find('.quiz-header');
        expect(quizHeader.text()).toBe('Test Quiz');
    });

    it('increments currentQuestionIndex on submitAnswer', async () => {
        const wrapper = mount(Quiz, {
            props: {
                selectedQuiz: {
                    questions: [
                        {problem: 'Question 1', solution: 'Answer 1', type: 'FILL_IN_THE_BLANKS'},
                        {problem: 'Question 2', solution: 'Answer 2', type: 'FILL_IN_THE_BLANKS'},
                    ],
                },
            },
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.submitAnswer('Answer 1');
        expect(wrapper.vm.currentQuestionIndex).toBe(1);
    });

    it('does not increment currentQuestionIndex when it is the last question', async () => {
        const wrapper = mount(Quiz, {
            props: {
                selectedQuiz: {
                    questions: [
                        {problem: 'Question 1', solution: 'Answer 1', type: 'FILL_IN_THE_BLANKS'},
                    ],
                },
            },
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.submitAnswer('Answer 1');
        expect(wrapper.vm.currentQuestionIndex).toBe(0);
    });

    it('sets quizCompleted to true when last question is answered', async () => {
        const wrapper = mount(Quiz, {
            props: {
                selectedQuiz: {
                    questions: [
                        {problem: 'Question 1', solution: 'Answer 1', type: 'FILL_IN_THE_BLANKS'},
                    ],
                },
            },
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.submitAnswer('Answer 1');
        expect(wrapper.vm.quizCompleted).toBe(true);
    });

    it('resets quiz on restartQuiz', async () => {
        const wrapper = mount(Quiz, {
            props: {
                selectedQuiz: {
                    questions: [
                        {problem: 'Question 1', solution: 'Answer 1', type: 'FILL_IN_THE_BLANKS'},
                        {problem: 'Question 2', solution: 'Answer 2', type: 'FILL_IN_THE_BLANKS'},
                    ],
                },
            },
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.submitAnswer('Answer 1');
        await wrapper.vm.restartQuiz();
        expect(wrapper.vm.currentQuestionIndex).toBe(0);
        expect(wrapper.vm.answers).toEqual([]);
        expect(wrapper.vm.quizCompleted).toBe(false);
        expect(wrapper.vm.showResults).toBe(false);
        expect(wrapper.vm.correctAnswersCount).toBe(0);
    });

    it('emits closeQuiz event when back button is clicked', async () => {
        const wrapper = mount(Quiz, {
            props: {
                selectedQuiz: {
                    questions: [],
                },
            },
            global: {
                plugins: [app],
            },
        });

        wrapper.vm.quizCompleted = true;
        await wrapper.vm.$nextTick();

        const backButton = wrapper.find('.back-to-quizes-button');
        await backButton.trigger('click');
        expect(wrapper.emitted()).toHaveProperty('closeQuiz');
    });
});