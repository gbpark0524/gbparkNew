import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router';
import Home from '@/views/Home.vue';
import Layout from '@/components/MainLayout.vue'
import Guestbook from "@/views/GuestbookView.vue";

const routes = [
    {
        path: '/',
        component: Layout,
        children: [
            {
                path: '/',
                component: Home
            },
            {
                path: 'guestbookMain',
                component: Guestbook
            }
        ]
    }
];

const router = createRouter({
    history: createWebHashHistory(process.env.baseURL),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = 'gbPark';
    next();
});

export default router;
