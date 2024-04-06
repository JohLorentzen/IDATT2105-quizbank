<script setup>
import { ref, computed } from 'vue'

const title = ref('Contact us')
const email = ref('')
const message = ref('')

const isValidEmail = (email) => {
    const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return regex.test(email);
}

const disableSubmit = computed(() => {
    return !isValidEmail(email.value) || message.value.trim() === '';
});

const submit = () => {
    console.log(email.value, message.value);
}
</script>

<template>
    <form class="form">
        <div class="title">Contact us</div>
        <input type="text" v-model="email" placeholder="Your email" class="input">
        <textarea v-model="message" placeholder="Your message"></textarea>
        
        <button :disabled="disableSubmit">Submit</button>
    </form>
</template>
<style scoped>
 .form {
  position: relative;
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  gap: 10px;
  width: 300px;
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 30px 30px -30px rgba(27, 26, 26, 0.315);
}

.form .title {
  color: royalblue;
  font-size: 30px;
  font-weight: 600;
  letter-spacing: -1px;
  line-height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form input {
  outline: 0;
  border: 1px solid rgb(219, 213, 213);
  padding: 8px 14px;
  border-radius: 8px;
  width: 100%;
  height: 50px;
}

.form textarea {
  border-radius: 8px;
  height: 100px;
  width: 100%;
  resize: none;
  outline: 0;
  padding: 8px 14px;
  border: 1px solid rgb(219, 213, 213);
}

.form button {
  align-self: flex-end;
  padding: 8px;
  outline: 0;
  border: 0;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  background-color: royalblue;
  color: #fff;
  cursor: pointer;
}
</style>