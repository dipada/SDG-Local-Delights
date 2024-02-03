<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Footer from "@/components/Footer.vue";
import 'leaflet/dist/leaflet.css';
import store from "@/store/index.js";

const token = localStorage.getItem('userToken'); // XSS vulnerability, use httpOnly cookies in production
if (token) {
  store.dispatch('saveUserInfo', token);
}
</script>

<template>
    <RouterView/>
    <Footer class="flex flex-col h-screen justify-between"/>
</template>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    background: #DAD7CD;
    color: #588157;
    width: 100%;
    height: auto;
}
</style>