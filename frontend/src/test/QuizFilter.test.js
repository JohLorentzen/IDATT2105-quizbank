import {describe, expect, it} from 'vitest';
import {createApp} from 'vue';
import {createPinia} from 'pinia';
import {mount} from '@vue/test-utils';
import QuizFilter from '@/components/QuizFilter.vue';

describe('QuizFilter', () => {
    const app = createApp({});
    app.use(createPinia());
    it('renders filter options correctly', () => {
        const wrapper = mount(QuizFilter, {
            global: {
                plugins: [app],
            },
        });

        const options = wrapper.findAll('option');
        expect(options.length).toBe(4);
        expect(options[0].text()).toBe('All');
        expect(options[1].text()).toBe('Easy');
        expect(options[2].text()).toBe('Medium');
        expect(options[3].text()).toBe('Hard');
    });

    it('updates searchTerm correctly', async () => {
        const wrapper = mount(QuizFilter, {
            global: {
                plugins: [app],
            },
        });

        await wrapper.find('input[type="text"]').setValue('test');
        expect(wrapper.vm.searchTerm).toBe('test');
    });

    it('updates chosenFilters correctly when a filter is chosen', async () => {
        const wrapper = mount(QuizFilter, {
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.chooseFilter('test');
        expect(wrapper.vm.chosenFilters).toContain('test');
    });

    it('updates chosenFilters correctly when a filter is removed', async () => {
        const wrapper = mount(QuizFilter, {
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.chooseFilter('test');
        await wrapper.vm.removeFilter('test');
        expect(wrapper.vm.chosenFilters).not.toContain('test');
    });

    it('emits the correct event when filterDifficulty is called', async () => {
        const wrapper = mount(QuizFilter, {
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.filterDifficulty();
        expect(wrapper.emitted()).toHaveProperty('updateDifficultyFilter');
    });

    it('emits the correct event when filterSharedStatus is called', async () => {
        const wrapper = mount(QuizFilter, {
            global: {
                plugins: [app],
            },
        });

        await wrapper.vm.filterSharedStatus();
        expect(wrapper.emitted()).toHaveProperty('updateSharedStatusFilter');
    });


})