import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import store from "@/store/index.js";
import axios from "axios";

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
            path: '/client/shops',
            name: 'client-shops',
            component: () => import('../views/client/ClientShopsView.vue'),
            meta: {requiresAuth: true}
        },
        {
            path: '/client/shops/:id',
            name: 'client-shop',
            component: () => import('../views/client/ClientShopView.vue'),
            meta: {requiresAuth: true}
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
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            // component: () => import('../views/AboutView.vue')
        }
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

//axios.interceptors.response.use(
  //  response => response,
   // error => {
    //    if (error.response && error.response.status === 401) {
     //       store.dispatch('logoutUser'); // user logout
      //      router.push({name: 'login'}); // redirect to login page
       // }
       // return Promise.reject(error);
   // }
//);




export default router
