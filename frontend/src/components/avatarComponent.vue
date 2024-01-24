<template>
  <button @click="userProfile" class="flex items-center gap-4 font-medium dark:text-white text-sm text-gray-500">
    <img :src="imageUrl" alt="" class="w-10 h-10 rounded-full" />
    {{ name }}
  </button>
</template>

<script>

export default {

  props: {
    name: {
      type: String,
      required: true
    },
    imageUrl: {
      type: String,
      required: true
    },
  },
  methods: {
    userProfile() {
      this.$router.push('/client/profile');
    }
  }
};


</script>