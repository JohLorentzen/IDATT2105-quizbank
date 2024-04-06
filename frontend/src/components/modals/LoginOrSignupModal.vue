<script setup>
import router from "@/router/index.js";
import routes from "@/router/router-constants.json";
function navigateTo(route) {
  router.push(route)
}
</script>

<template>
  <div class="modal-bg" @click="navigateTo(routes.HOME)">
    <div class="modal-container" @click.stop="">
      <FontAwesomeIcon icon="xmark" class="fa-2x cross-icon" @click="navigateTo(routes.HOME)" />
      <h1>Not so fast!</h1>
      <p>Login or create a user to get started!</p>
      <div class="button-container">
        <button class="login-btn" @click="navigateTo(routes.LOGIN)">Login</button>
        <button class="sign-up-btn" @click="navigateTo(routes.LOGIN)">Sign up</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-bg {
  position: fixed;
  z-index: 100;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  display: flex;
  background-color: var(--bg-very-light-blue-shadow);
  justify-content: center;
  align-items: center;
  padding: 0 10%;
}

.modal-container {
  z-index: 1000;
  max-width: 400px;
  background: var(--text-color-white);
  padding: 2.6em 3.6em;
  border-radius: 1em;
  text-align: center;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
  position: relative;
}

.cross-icon {
  color: grey;
  position: absolute;
  top: 0.6em;
  right: 0.6em;
}

.cross-icon:hover {
  color: var(--text-color-grey);
  cursor: pointer;
}

h1 {
  font-weight: bold;
  font-size: 3rem;
  letter-spacing: 0.03em;
  margin-top: 0.2em;
}

p {
  font-size: 1rem;
  margin-top: 0.8em;
  line-height: 1.5em;
}

.button-container {
  margin-top: 1em;
  display: flex;
  justify-content: center;
  gap: 1em
}
button {
  border: none;
  padding: 1em 2em;
  border-radius: 0.3em;
  letter-spacing: 0.1em;
  font-weight: bold;
  text-transform: uppercase;
}

button:hover {
  cursor: pointer;
}

.login-btn {
  color: var(--button-bg-strong-blue);
  background: var(--text-color-white);
  border: 1px solid var(--button-bg-strong-blue);
}

.login-btn:hover {
  background-color: var(--bg-white);
  color: var(--text-color)
}

.sign-up-btn {
  background: var(--button-bg-strong-blue);
  color: var(--text-color-white)
}

.sign-up-btn:hover {
  background-color: var(--button-bg-hover-blue);
  color: var(--bg-white)
}
</style>