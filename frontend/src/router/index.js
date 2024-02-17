import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import store from "@/store/index.js";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView,
            beforeEnter(to, from, next) {
                // if logged go to /client/home
                if (store.state.userToken) {
                    next({name: 'client-home'});
                } else {
                    next();
                }
            }
        },
        {
            path: '/redirect/oauth',
            name: 'redirect-oauth',
            component: () => import('../views/RedirectOAuthView.vue')
        },
        {
            path: '/client/profile',
            name: 'client-profile',
            component: () => import('../views/client/ClientProfileView.vue'),
            meta: {requiresAuth: true}
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/LoginView.vue')
        },
        {
            path: '/sign-up',
            name: 'sign-up',
            component: () => import('../views/SignUpView.vue')
        },

        {
            path: '/client/home',
            name: 'client-home',
            component: () => import('../views/client/ClientHomeView.vue'),
            meta: {requiresAuth: true}
        },
        {
            path: '/client/shops', // list of shops view
            name: 'client-shops',
            component: () => import('../views/client/ClientShopsView.vue'),
            meta: {requiresAuth: true}
        },
        {
            path: '/client/shop', // single shop view
            name: 'client-shop',
            component: () => import('../views/client/ClientShopView.vue'),
            meta: {requiresAuth: true}
        },
        {
            path: '/client/cart',
            name: 'client-cart',
            component: () => import('../views/client/ClientCartView.vue'),
        },

        {
            path: '/seller/home',
            name: 'seller-home',
            component: () => import('../views/seller/SellerHomeView.vue'),
            meta: {requiresAuth: true}
        },
        {
            path: '/client/map',
            name: 'client-map',
            component: () => import('../views/client/MapShopsView.vue'),
        },
        {
            path: '/seller/shop',
            name: 'seller-shop',
            component: () => import('../views/seller/ShopView.vue'),
        },

        {
            path: '/prova',
            name: 'prova',
            component: () => import('../views/client/RicercaInLat.vue'),
        },
    ]
});

router.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    const isAuthenticated = store.getters.isAuthenticated;

    if (requiresAuth && !isAuthenticated) {
        next({name: 'login'});// not logged, redirect to login page
    } else {
        next();
    }
});

export default router
