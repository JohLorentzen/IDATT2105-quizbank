<script setup>
import { ref } from 'vue';

const events = ref([
  { date: '21. March 2024', content: 'The task was given to us, and all three in the team where eager to start' },
  { date: '26. March 2024', content: 'MVP, the first sign of real progress as the team connected a functional frontend webpage with a operational backend API.' },
  { date: '28. March 2024', content: 'We started adding more features and quality of life improvments, this made quiz creation easier.' },
  { date: '5. April 2024', content: ' We finially added support for collabiration on quiz, another great way to share the knowlegde!' },
  { date: '8. April 2024', content: 'Deadline, and the project was finished, we where all happy with the result!'},
]);
const selectedEvent = ref(null);

const selectEvent = (index) => {
  if (selectedEvent.value === index) {
    selectedEvent.value = null; 
  } else {
    selectedEvent.value = index;
  }
};
</script>
<template>
  <main>
    <section class="welcome-section">
      <h1>Welcome to QuizBank</h1>
      <h2>Explore the history of QuizBank from the humble beginning until today!</h2>
    </section>

    <section class="timeline-section">
      <div class="timeline-container">
        <div v-for="(event, index) in events" :key="index" class="timeline-item" @click="selectEvent(index)">
          <div class="date">{{ event.date }}</div>
          <div class="dot"></div>
          <div class="line"></div>
          <div v-if="selectedEvent === index" class="content">{{ event.content }}</div>
        </div>
        
      </div>
    </section>

    <section class="process-content">
      <h3>About our process</h3>
      <p>
        We started this project with a clear goal in mind, the project would raise our grade in IDATT2105.
        We have divided the work between all three of us, and we have all contributed to the project.
        Since the project spaned over easter, did we need to plan our work carefully, and we have had a lot of meetings to make sure we are on track.
        The best part of the planning was the creation of the spread sheet to dived the work and see what everyone was working on.
        This saved us a lot of time and made the collabiration easier.
      </p>
    </section>
    <section class="thankyou-content">
      <h3>Thank you!</h3>
      <p>
        We really apreciate the opportunity to work on this project, and we are all happy with the result.
        I think everyone has seen this project as a challenge, and a great to gain more knowlegde in fields we were not as familiar with.
        A special thank you to the teachers:
        <br>
        <strong>Alexander Holt, Tomas Holt, Surya Bahadur Kathayat, Muhammad Ali Norozi and the TA's for the help and support.</strong>
        <br>
        Could not have done it without you!
      </p>
    </section>
  </main>
</template>

<style scoped>
.welcome-section h1 {
  font-size: 3rem;
  color: #2c3e50;
  text-align: center;
}

.welcome-section h2 {
  font-size: 1.5rem;
  color: #34495e;
  text-align: center;
  margin-bottom: 2rem;
}

.timeline-section {
  display: flex;
  justify-content: center;
}

.timeline-container {
  display: flex;
  align-items: center;
  overflow-x: auto;
  margin: 2rem 0;
}

.timeline-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 3rem;
}

.date {
  font-weight: bold;
  color: #8e44ad;
  margin-bottom: 10px;
}

.line {
  position: absolute;
  height: 4px;
  background-color: #8e44ad;
  width: 100%;
  top: 10px;
  z-index: 0;
}

.dot {
  height: 20px;
  width: 20px;
  background-color: #8e44ad;
  border-radius: 50%;
  position: relative;
  z-index: 1;
}

.content {
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-left: 2rem;
  transition: all 0.3s ease;
}

.content::before {
  content: '';
  position: absolute;
  top: 10px;
  left: -15px;
  border: 10px solid transparent;
  border-right-color: white;
}
.process-content h3 {
  font-size: 1.5rem;
  color: #2c3e50;
  text-align: center;
}

.process-content p {
  text-align: justify;
  margin: 1rem 20%;
}

.thankyou-content {

  border-top: 2px solid #e9ecef; 
  padding: 2rem 0; 
  text-align: center; 
}

.thankyou-content h3 {
  color: #343a40; 
  margin-bottom: 1rem;
  font-size: 2.5rem; 
}

.thankyou-content p {
  color: #495057; 
  line-height: 1.6; 
  max-width: 800px; 
  margin: auto; 
  font-size: 1.1rem;
}

.thankyou-content p strong {
  color: #007bff;
  font-weight: bold;
}

.thankyou-content br {
  margin-bottom: 0.5rem; 
  display: block; 
}

@media (max-width: 600px) {
  .process-content p {
    margin: 1rem 10%;
  }
}

</style>
