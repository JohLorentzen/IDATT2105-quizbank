<template>
  <main>
    <div class="not-found-container">
      <h1>Oops!</h1>
      <h2>404 - Page Not Found</h2>
      <p>The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p>
      <div class="action-buttons">
        <router-link to="/" class="home-link">Back to safe grounds</router-link>
        <router-link to="/contact" class="report-link">Report a bug</router-link>
      </div>
    </div>
  </main>
  </template>
  
  <style scoped>
  .not-found-container {
    text-align: center;
    padding: 2rem;
    background-color: var(--background-color);
    border-radius: 10px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  }
  
  h1 {
    font-size: 3rem;
    color: var(--primary-color);
  }
  
  h2 {
    font-size: 2rem;
    margin-bottom: 2rem;
  }
  
  .action-buttons {
    padding-top: 2rem;
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin-top: 2rem;
  }
  
  .router-link {
  display: inline-block;
  background-color: #031e3a;
  color: #fff;
  padding: 10px 20px;
  margin: 10px;
  border-radius: 5px;
  font-weight: bold;
  box-shadow: 0px 2px 5px rgba(0, 123, 255, 0.5);
  transition: all 0.3s ease;
}


</style>
  