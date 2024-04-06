import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/quiz',
      name: 'quiz',
      component: () => import('../views/QuizView.vue')
    },
    {
      path: '/createEdit',
      name: 'createEdit',
      component: () => import('../views/CreateEdit.vue')
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue')
    },
    {
      path: '/profile/stats',
      name: 'profileStats',
      component: () => import('../components/profile/ProfileStats.vue'),
    },
    {
      path: '/profile/edit',
      name: 'editProfile',
      component: () => import('../components/profile/ProfileEdit.vue'),
    },
    {
      path: '/quizAttempts',
      name: 'quizAttempts',
      component: () => import('../views/QuizAttempts.vue')
    },
    {
      path: '/contact',
      name: 'contact',
      component: () => import('../views/Contact.vue')
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: () => import('../views/NotFound.vue')
    },
    {
      path: '/login-or-signup',
      name: 'login-or-signup',
      component: () => import('../components/modals/LoginOrSignupModal.vue')
    }
  ],
  linkActiveClass: 'active',
})

export default router
