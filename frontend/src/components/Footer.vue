<script setup>
</script>
<template>
  <footer class="footer">
    <div class="footer-upper">
      <div class="footer-nav">
        <h3>Links</h3>
        <router-link to="/login">Log In</router-link>
        <router-link to="/createEdit">Create quiz</router-link>
        <router-link to="/about">Contact</router-link>
        <router-link to="/about">About Us</router-link>
        <!-- Add more links as needed -->
      </div>
      <div>
        <h3>Follow us</h3>
        <div class="social-links">
          
        </div>
      </div>
      <div class="footer-newsletter">
        <h3>Newsletter</h3>
        <input type="email" placeholder="Your email here">
        <button>Subscribe</button>
      </div>
    </div>
  </footer>
</template>
<style scoped>
.footer {
  background-color: #333;
  color: white;
  font-size: 14px;
  grid-column: 1 / -1;
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  padding: 1.5em 0;
}

.footer-upper {
  grid-column: 2 / -2;
  display: flex;
  justify-content: space-between;
}

.footer-nav, .footer-newsletter {
  display: flex;
  flex-direction: column;
}

.footer-nav h3, .footer-newsletter h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 0.5em;
}

.footer-nav a, .footer-newsletter button {
  margin-bottom: 5px;
  color: white;
  text-decoration: none;
}

.footer-newsletter input[type='email'] {
  padding: 5px;
}

.footer-newsletter button {
  margin-top: 0.8em;
  background-color: var(--text-color-light-grey);
  border: none;
  padding: 0.4em 0.8em;
  border-radius: 0.2em;
  cursor: pointer;
}

.footer-newsletter button:hover {
  background-color: var(--text-color-grey);
}

.social-links {
  display: flex;
}

.social-links .icon {
  width: 20px;
  height: 20px;
  background-size: contain;
  margin-right: 10px;
  /* Add background images for each icon */
}

.legal-links {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.legal-links a {
  color: white;
  text-decoration: none;
}

@media (min-width: 1480px) {
  .footer {
    grid-template-columns: 1fr 1280px 1fr;
  }
}
</style>
