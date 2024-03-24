import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import { createRouter, createMemoryHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue'; // Adjust the path to your view

// Create a mock router
const router = createRouter({
  history: createMemoryHistory(),
  routes: [{ path: '/quiz', component: { template: '<div>Quiz</div>' } }],
});

describe('HomeView', () => {
  it('renders correctly with Heroimage and other elements', async () => {
    const wrapper = mount(HomeView, {
      global: {
        plugins: [router],
      },
    });

    // Check if Heroimage component is rendered with correct props
    const heroImage = wrapper.findComponent({ name: 'Heroimage' });
    expect(heroImage.exists()).toBe(true);
    expect(heroImage.props('imageUrl')).toBe('https://images.pexels.com/photos/3153198/pexels-photo-3153198.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2');

    // Check for static text content
    expect(wrapper.find('h1').text()).toContain('Home');
    expect(wrapper.find('h2').text()).toContain('What is Quizbank?');
    expect(wrapper.find('p').text()).toContain('A better way to create, share and learn!');

    // Check if button click routes to /quiz
    await wrapper.find('button').trigger('click');
    await router.isReady();
    expect(router.currentRoute.value.path).toBe('/quiz');
  });
});
