<script>
import store from "@/store/index.js";

export default {
  name: "LogoutButtonComponent",
  methods: {
    logoutUser() {
      store.dispatch('logoutUser')
      window.location.href = 'http://localhost:30080/auth/logout'
    }
  }
}
</script>

<template>
  <button @click="logoutUser" type="button" class="text-white bg-secondary hover:bg-green-800 focus:ring-4 focus:outline-none peer-focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-green-800 dark:hover:bg-green-700 dark:focus:ring-offset-green-300">
    <svg class="w-3.5 h-3.5 me-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 21">
      <path d="M8.00171 7C8.01382 4.82497 8.11027 3.64706 8.87865 2.87868C9.75733 2 11.1715 2 14 2H15C17.8284 2 19.2426 2 20.1213 2.87868C21 3.75736 21 5.17157 21 8V16C21 18.8284 21 20.2426 20.1213 21.1213C19.2426 22 17.8284 22 15 22H14C11.1715 22 9.75733 22 8.87865 21.1213C8.11027 20.3529 8.01382 19.175 8.00171 17" stroke="#1C274C" stroke-width="1.5" stroke-linecap="round"/>
      <path opacity="0.5" d="M8 19.5C5.64298 19.5 4.46447 19.5 3.73223 18.7678C3 18.0355 3 16.857 3 14.5V9.5C3 7.14298 3 5.96447 3.73223 5.23223C4.46447 4.5 5.64298 4.5 8 4.5" stroke="#1C274C" stroke-width="1.5"/>
      <path d="M15 12L6 12M6 12L8 14M6 12L8 10" stroke="#1C274C" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>
    Logout
  </button>
</template>

<style scoped>

</style>