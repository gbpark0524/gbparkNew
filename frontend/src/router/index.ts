import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../views/Home.vue';

const routes = [
    {
        path: '/',
        name: 'home-page',
        component: HomePage
    }
];

const router = createRouter({
    history: createWebHistory(process.env.baseURL),
    routes
});

export default router;
