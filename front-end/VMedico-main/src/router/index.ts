import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const Home = () => import('../pages/Home.vue')
const About = () => import('../pages/About.vue')
const Login = () => import('../pages/Login.vue')
const Dashboard = () => import('../pages/Dashboard.vue')
const Register = () => import('../pages/Register.vue')

const routes: RouteRecordRaw[] = [
  { path: '/', name: 'home', component: Home },
  { path: '/about', name: 'about', component: About },
  { path: '/login', name: 'login', component: Login },
  { path: '/register', name: 'register', component: Register },
   { path: '/dashboard', component: Dashboard },
  { path: '/:pathMatch(.*)*', redirect: '/' },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  linkActiveClass: 'text-egypt-700',
  scrollBehavior() {
    return { top: 0 }
  },
})

export default router


