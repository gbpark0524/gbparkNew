import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Layout from '@/components/MainLayout.vue'

const routes = [
    {
        path: '/',
        component: Layout,
        children: [
            {
                path: '',
                component: Home
            },
        ]
    }
];

const router = createRouter({
    history: createWebHistory(process.env.baseURL),
    routes
});

export default router;
