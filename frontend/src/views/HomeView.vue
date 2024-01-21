<script setup>
import HeaderHomeComponent from "@/components/HeaderHomeComponent.vue";
import CardHome from "@/components/CardHome.vue";
import fruitImg from "@/assets/fruit.svg";
import vegetablesImg from "@/assets/vegetables.svg";
import pastaImg from "@/assets/pasta.svg";
import breadImg from "@/assets/bread.svg";
import eggsImg from "@/assets/eggs.svg";
import meatImg from "@/assets/meat.svg";

</script>

<template>
  <HeaderHomeComponent class="z-10"/>
  <div class="flex flex-col items-center justify-center bg-neutral-100">
    <img width="1280" src="../assets/cover.jpg" alt="">
  </div>
<div class="flex-col justify-center mx-auto items-center">
  <h2 class="py-10 text-3xl font-bold text-center text-green-800">Large variety of products</h2>

  <div class="container flex flex-col md:flex-row justify-center mx-auto items-center">
    <CardHome class="m-4" :image="fruitImg" title="Fruit"/>
    <CardHome class="m-4" :image="vegetablesImg" title="Vegetables"/>
    <CardHome class="m-4" :image="pastaImg" title="Pasta"/>
    <CardHome class="m-4" :image="breadImg" title="Bread"/>
    <CardHome class="m-4" :image="eggsImg" title="Eggs"/>
    <CardHome class="m-4" :image="meatImg" title="Meat"/>
  </div>
</div>
</template>

<style scoped>

</style>