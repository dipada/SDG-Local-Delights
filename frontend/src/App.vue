<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Footer from "@/components/Footer.vue";

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