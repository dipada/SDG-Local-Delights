<script setup>

import SdgLogo from "@/components/icons/sdgLogo.vue";
import {computed, ref} from "vue";
import store from "@/store/index.js";
import axios from "axios";

//TODO REMOVE
// prende il token da vuex store e lo passa al componente footer
const token = computed(() => store.getters.getUserToken);
// fa is autenticated
const isAutenticated = computed(() => store.getters.isAuthenticated);
const userInfo = computed(() => store.getters.getUserInfo);
const welcomeMessage2 = ref('');

const fetchWelcomeMessage2 = async () => {
  try {
    const response = await axios.get('http://localhost:30085/api/v1/user/client/dani96dipalma@gmail.com', {
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    });
    console.log("da welcome 2 --> " , response);
    console.log("da welcome 2 DATA --> " , response.data);
    welcomeMessage2.value = response.data.message;
  } catch (error) {
    console.error(error);
  }
}

fetchWelcomeMessage2();

</script>

<template>
  <footer class="rounded-lg shadow dark:bg-gray-900 m-4">
    <div class="w-full max-w-screen-xl mx-auto p-4 md:py-8">
      <div class="sm:flex sm:items-center sm:justify-between">
        <a href="#" class="flex items-center mb-4 sm:mb-0 space-x-3 rtl:space-x-reverse">
          <sdg-logo/>
        </a>
        <ul class="flex flex-wrap items-center mb-6 text-md font-medium text-secondary sm:mb-0 dark:text-gray-400">
          <li>
            <a href="#" class="hover:underline me-4 md:me-6">About</a>
          </li>
          <li>
            <a href="#" class="hover:underline me-4 md:me-6">Privacy Policy</a>
          </li>
          <li>
            <a href="#" class="hover:underline me-4 md:me-6">Licensing</a>
          </li>
          <li>
            <a href="#" class="hover:underline">Contact</a>
          </li>
        </ul>
      </div>
      <hr class="my-6 border-gray-200 sm:mx-auto dark:border-gray-700 lg:my-8" />
      <span class="block text-md text-secondary md:text-center dark:text-gray-400">© 2024 <a href="" class="hover:underline">SDGLocalDelights™</a>. All Rights Reserved.</span>
    </div>
    <div class="bg-black text-white">token: {{token}}</div><br/>
    <div class="bg-black text-white">autenticated: {{isAutenticated}}</div><br/>
    <div class="bg-black text-white">userInfo: {{userInfo}}</div>

    <div class="bg-black text-white">WELCOME-2: {{welcomeMessage2}}</div><br/>
  </footer>
</template>

<style scoped>

</style>