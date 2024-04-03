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
  padding: 20px;
  font-size: 14px;
}

.footer-upper, .footer-lower {
  display: flex;
  justify-content: space-between;
}

.footer-nav, .footer-newsletter {
  display: flex;
  flex-direction: column;
}

.footer-nav h3, .footer-newsletter h3 {
  margin-bottom: 10px;
}

.footer-nav a, .footer-newsletter button {
  margin-bottom: 5px;
  color: white;
  text-decoration: none;
}

.footer-newsletter input[type='email'] {
  padding: 5px;
  margin-bottom: 5px;
}

.footer-newsletter button {
  cursor: pointer;
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
</style>
