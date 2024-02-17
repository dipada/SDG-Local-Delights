<template>
  <div class="flex justify-center items-center h-screen flex-grow">
    <div v-if="isLoading" class="spinner-border animate-spin inline-block w-80 h-80 border-8 rounded-full" role="status">
      <span class="visually-hidden"></span>
    </div>
  </div>
</template>

<script>
import {mapActions} from 'vuex';

export default {
  data() {
    return {
      isLoading: true,
    };
  },
  methods: {
    ...mapActions(['saveUserInfo']),
  },
  async created() {
    const token = this.$route.query.token;
    if (token) {
      await this.saveUserInfo(token);

      setTimeout(() => {
        this.isLoading = false;
        this.$router.push('/client/home');
      }, 5000);
    } else {
      console.log('No token found');
      this.isLoading = false;
    }
  },
};
</script>

<style>
.spinner-border {
  border-color: transparent;
  border-top-color: #588157;
}
</style>
