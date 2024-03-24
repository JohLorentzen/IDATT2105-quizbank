import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import Heroimage from '@/components/Heroimage.vue'; 

describe('Heroimage', () => {
  it('renders with the provided imageUrl prop', () => {
    const imageUrl = 'https://cdn.pixabay.com/photo/2014/06/03/19/38/board-361516_1280.jpg';
    const wrapper = mount(Heroimage, {
      props: { imageUrl },
    });

    const heroImageDiv = wrapper.find('.hero-image');

    // Check if the backgroundImage style is correctly applied
    expect(heroImageDiv.attributes('style')).toContain(`background-image: url(${imageUrl})`);

    // Check for static text
    expect(wrapper.text()).toContain('Quizbank');
    expect(wrapper.text()).toContain('A better way to create, share and learn!!');
  });
});
