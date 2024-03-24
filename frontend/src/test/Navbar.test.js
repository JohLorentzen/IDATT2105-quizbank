import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import { createRouter, createWebHistory } from 'vue-router';
import Navbar from '@/components/Navbar.vue'; 

// Mock routes for testing purposes
const routes = [
  { path: '/', component: { template: '<div>Home</div>' } },
  { path: '/about', component: { template: '<div>About</div>' } },
  { path: '/login', component: { template: '<div>Login</div>' } },
];

// Create a router instance
const router = createRouter({
  history: createWebHistory(),
  routes,
});

describe('Navbar', () => {
  it('renders router links correctly', async () => {
    const wrapper = mount(Navbar, {
      global: {
        plugins: [router],
      },
    });

    // Assert the existence of the RouterLinks and their 'to' attributes
    const links = wrapper.findAllComponents({ name: 'RouterLink' });
    expect(links.length).toBe(3);
    expect(links[0].props('to')).toBe('/');
    expect(links[1].props('to')).toBe('/about');
    expect(links[2].props('to')).toBe('/login');

    // Optionally, you can also check for the correct rendering of link text
    expect(links[0].text()).toContain('Home');
    expect(links[1].text()).toContain('About');
    expect(links[2].text()).toContain('Login');
  });
});
