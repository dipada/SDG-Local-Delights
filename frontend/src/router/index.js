import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/redirect/oauth',
        name: 'redirect-oauth',
        component: () => import('../views/RedirectOAuthView.vue')
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
        component: () => import('../views/client/ClientHomeView.vue')
    },
    {
      path: '/client/shops',
        name: 'client-shops',
        component: () => import('../views/client/ClientShopsView.vue')
    },
    {
      path: '/client/shops/:id',
        name: 'client-shop',
        component: () => import('../views/client/ClientShopView.vue')
    },

    {
      path: '/seller/home',
      name: 'seller-home',
      component: () => import('../views/seller/SellerHomeView.vue')
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
})

export default router
