<script setup></script>
<template>
  <footer class="footer">
    <div class="footer-upper">
      <div class="footer-nav">
        <h3>Links</h3>
        <router-link to="/login">Log In</router-link>
        <router-link to="/createEdit">Create quiz</router-link>
        <router-link to="/contact">Contact</router-link>
        <router-link to="/about">About Us</router-link>
        <!-- Add more links as needed -->
      </div>
      <div class="soMe">
        <h3>Follow us</h3>
        <div class="social-links">
          <a href="https://twitter.com/" alt="twitter link" target="_blank">
            <FontAwesomeIcon icon="fa-brands fa-x-twitter" class="fa-3x" />
          </a>
          <a href="https://www.facebook.com/" alt="facebook link" target="_blank">
            <FontAwesomeIcon icon="fa-brands fa-facebook" class="fa-3x" />
          </a>
          <a
            href="https://www.instagram.com/" alt="instagram link"
            target="_blank"
          >
            <FontAwesomeIcon icon="fa-brands fa-instagram" class="fa-3x" />
          </a>
          <a
            href="https://www.linkedin.com/in/" alt="linkedin link"
            target="_blank"
          >
            <FontAwesomeIcon icon="fa-brands fa-linkedin" class="fa-3x" />
          </a>
        </div>
      </div>

      <div class="footer-newsletter">
        <h3>Newsletter</h3>
        <input type="email" placeholder="Your email here" />
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

.footer-nav,
.footer-newsletter {
  display: flex;
  flex-direction: column;
}

.footer-nav h3,
.footer-newsletter h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 0.5em;
}

.footer-nav a,
.footer-newsletter button {
  margin-bottom: 5px;
  color: white;
  text-decoration: none;
}

.footer-newsletter input[type="email"] {
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

.soMe {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.soMe h3 {
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.social-links {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
}

.social-links a {
  color: white;
}

.social-links .fa-3x {
  font-size: 3em;
}

.legal-links {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}



@media (min-width: 1480px) {
  .footer {
    grid-template-columns: 1fr 1280px 1fr;
  }
}
</style>
