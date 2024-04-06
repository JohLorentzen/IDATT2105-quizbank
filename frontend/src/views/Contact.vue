<script setup>
import ContactForm from '../components/ContactForm.vue';
</script>
<template>
    <main class="contact-container">
        <h2 class="contact-title">Submit your feedback and tell us how you feel!</h2>
        <ContactForm />
    </main>
</template>
<style scoped>
.contact-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.contact-title {
    font-size: 24px;
    margin-bottom: 20px;
}

</style>
