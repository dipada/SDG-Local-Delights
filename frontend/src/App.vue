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
  <div class="flex flex-col min-h-screen">
    <div class="flex-grow">
      <RouterView/>
    </div>
    <Footer class="mt-auto"/>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  margin: 0;
  overflow: hidden;
}

#app {
  background: #DAD7CD;
  color: #588157;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.flex-grow {
  flex: 1;
}

footer {
  background: #DAD7CD;
  color: #588157;
}
</style>
