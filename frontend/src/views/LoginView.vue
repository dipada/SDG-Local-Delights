<script setup>
import HeaderBase from "@/components/HeaderBase.vue";
import LoginComponent from "@/components/LoginComponent.vue";
</script>

<template>
  <HeaderBase />
  <div class="flex flex-col lg:flex-row items-center justify-around mx-auto">
    <img
        class="hidden lg:block lg:w-2/3"
        src="../assets/contadinoLogin.svg"
        alt="contadinoLogin"
    >
    <LoginComponent class="bg-primary max-w-fit max-h-fit z-10" />
  </div>
</template>




<style scoped>

</style>