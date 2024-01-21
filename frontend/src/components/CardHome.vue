<script setup>
import {defineProps} from 'vue'

const props = defineProps({
  image: {
    type: String,
    default: ""
  },
  title: String,
})
</script>

<template>
  <div class="border-b-2 border-secondary max-w-sm bg-white border rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
    <a href="#">
      <img class="rounded-t-lg" :src="image" alt="" />
    </a>
    <div class="p-5">
      <a href="#">
        <h5 class="flex justify-center mb-2 text-2xl font-bold tracking-tight text-green-800 dark:text-white">{{title}}</h5>
      </a>
    </div>
  </div>

</template>

<style scoped>

</style>