<script setup>
import {defineProps} from 'vue'

const props = defineProps({
  image: {
    type: String,
    default: ""
  },
  title: String,
  description: {
    type: String,
    default: ""
  },
})
</script>

<template>
  <div class="border-b-2 border-secondary max-w-sm bg-white border rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
    <a href="#" class="items-center justify-center flex">
      <img class="items-center rounded-t-lg" :src="image" alt="" />
    </a>
    <div class="p-5">
      <a href="#">
        <h5 class="flex justify-center mb-2 text-2xl font-bold tracking-tight text-green-800 dark:text-white">{{title}}</h5>
      </a>
    </div>
    <p class="px-5 mb-5 text-base text-green-800 dark:text-gray-300">{{description}}</p>
  </div>

</template>

<style scoped>

</style>