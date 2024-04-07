import {mount} from '@vue/test-utils';
import QuizSummary from '@/components/QuizSummary.vue';
import {createApp} from 'vue';
import {createPinia} from "pinia";
import {describe, expect, it} from 'vitest';

describe('QuizSummary', () => {
    const app = createApp({});
    app.use(createPinia());

    it('renders questions and answers correctly', () => {
        const wrapper = mount(QuizSummary, {
            props: {
                questions: [
                    {problem: 'Question 1', solution: 'Answer 1'},
                    {problem: 'Question 2', solution: 'Answer 2'},
                ],
                answers: [
                    {submittedAnswer: 'Answer 1', isCorrect: true},
                    {submittedAnswer: 'Wrong Answer', isCorrect: false},
                ],
            },
            global: {
                plugins: [app],
            },
        });

        const rows = wrapper.findAll('tbody > tr');
        expect(rows.length).toBe(2);
        expect(rows[0].text()).toContain('Question 1');
        expect(rows[0].text()).toContain('Answer 1');
        expect(rows[0].text()).toContain('correct');
        expect(rows[1].text()).toContain('Question 2');
        expect(rows[1].text()).toContain('Wrong Answer');
        expect(rows[1].text()).toContain('incorrect');
    });

    it('calculates score correctly', () => {
        const wrapper = mount(QuizSummary, {
            props: {
                questions: new Array(5).fill({}),
                answers: [
                    {isCorrect: true},
                    {isCorrect: true},
                    {isCorrect: false},
                    {isCorrect: false},
                    {isCorrect: false},
                ],
            },
            global: {
                plugins: [app],
            },
        });

        expect(wrapper.find('.score-summary').text()).toBe('You got 2 / 5 correct answers');
    });

    it('emits toQuizzes event when back button is clicked', async () => {
        const wrapper = mount(QuizSummary, {
            props: {
                questions: [],
                answers: [],
            },
            global: {
                plugins: [app],
            },
        });

        await wrapper.find('button').trigger('click');
        expect(wrapper.emitted()).toHaveProperty('toQuizzes');
    });
});