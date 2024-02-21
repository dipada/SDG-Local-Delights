<script>
export default{
  name: 'DeliveryButtonComponent',
  methods: {
    navigateDelivery() {
      this.$router.push({name: 'delivery-available-orders'});
    }
  }
}
</script>

<template>
  <button @click="navigateDelivery" type="button" class="text-white bg-secondary hover:bg-green-800 focus:ring-4 focus:outline-none peer-focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-green-800 dark:hover:bg-green-700 dark:focus:ring-offset-green-300">
    <svg fill="currentColor" class="w-5 h-5 me-2" viewBox="0 0 24 24" id="Layer_1" data-name="Layer 1" xmlns="http://www.w3.org/2000/svg"><circle class="cls-1" cx="5.78" cy="16.33" r="2.39"/><path class="cls-1" d="M1.48,23.5V23a4.29,4.29,0,0,1,4.3-4.3h0a4.29,4.29,0,0,1,4.3,4.3v.48"/><polyline class="cls-1" points="15.71 16.69 18.69 13.7 21.68 16.69"/><path class="cls-1" d="M18.69,13.93V16.8A5.74,5.74,0,0,1,13,22.54h0"/><polyline class="cls-1" points="7.33 8.31 4.34 11.3 1.36 8.31"/><path class="cls-1" d="M4.35,11.07V8.2a5.73,5.73,0,0,1,5.73-5.74h0"/><rect class="cls-1" x="12.95" y="1.5" width="9.57" height="8.61"/><line class="cls-1" x1="17.74" y1="4.37" x2="17.74" y2="1.5"/></svg>
  </button>
</template>

<style scoped>

</style>