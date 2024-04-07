import {describe, expect, it} from 'vitest';
import {createApp} from 'vue';
import {createPinia} from 'pinia';
import {mount} from '@vue/test-utils';
import ShareModal from '@/components/ShareModal.vue';

describe('ShareModal', () => {
    const app = createApp({});
    app.use(createPinia());

    it('renders quiz name correctly', () => {
        const wrapper = mount(ShareModal, {
            props: {
                quiz: {
                    quizName: 'Test Quiz',
                },
            },
            global: {
                plugins: [app],
            },
        });

        const quizName = wrapper.find('p');
        expect(quizName.text()).toContain('Test Quiz');
    });

    it('updates viewerOrEditor correctly', async () => {
        const wrapper = mount(ShareModal, {
            props: {
                quiz: {},
            },
            global: {
                plugins: [app],
            },
        });

        await wrapper.find('select').setValue('editor');
        expect(wrapper.vm.viewerOrEditor).toBe('editor');
    });

    it('emits close event when close button is clicked', async () => {
        const wrapper = mount(ShareModal, {
            props: {
                quiz: {},
            },
            global: {
                plugins: [app],
            },
        });

        const closeButton = wrapper.find('#close-button');
        await closeButton.trigger('click');
        expect(wrapper.emitted()).toHaveProperty('close');
    });

});