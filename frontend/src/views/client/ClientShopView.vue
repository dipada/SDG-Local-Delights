<template>
  <div>
    <h1 class="text-black">Negozio ID: {{ shopId }}</h1>
  </div>
</template>

<script>
import {mapState} from 'vuex';

export default {
  name: 'ClientShopView',
  computed: {
    ...mapState({
      shopId: state => state.shopId
    })
  }
};
</script>