<script>
export default {
  props: {
    orders: {
      type: Array,
      required: true
    }
  },
  methods: {
    getStatusClass(status) {
      const normalizedStatus = status.toUpperCase();
      switch (normalizedStatus) {
        case 'PENDING':
          return 'bg-blue-600';
        case 'TO_BE_DELIVERED':
        case 'TO_BE_PICKED_UP':
          return 'bg-yellow-600';
        case 'COMPLETED':
          return 'bg-green-600';
        case 'CANCELLED':
          return 'bg-red-600';
        default:
          return 'bg-gray-600';
      }
    }
  }
}


</script>

<template>
  <div class="w-screen">
    <div class="mx-auto mt-8 max-w-screen-lg px-2">
      <div class="sm:flex sm:items-center sm:justify-between flex-col sm:flex-row">
        <p class="flex-1 text-base font-bold text-gray-900">Your orders</p>
      </div>

      <div class="mt-6 overflow-hidden rounded-xl border shadow">
        <table class="min-w-full border-separate border-spacing-y-2 border-spacing-x-2">
          <thead class="hidden border-b lg:table-header-group">
          <tr>
            <td class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-6">Shop</td>
            <td class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-6">Date</td>
            <td class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-6">Amount</td>
            <td class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-6">Status</td>
          </tr>
          </thead>
          <tbody class="lg:border-gray-300">
          <tr v-for="order in orders" :key="order.id">
            <td class="whitespace-no-wrap py-4 text-sm font-bold text-gray-900 sm:px-6">
              {{ order.shopname }}
              <div class="mt-1 lg:hidden">
                <p class="font-normal text-gray-500">{{ order.date }}</p>
              </div>
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-500 sm:px-6 lg:table-cell">
              {{ order.date }}
            </td>
            <td class="whitespace-no-wrap py-4 px-6 text-right text-sm text-gray-600 lg:text-left">
              {{ order.amount }}
              <div :class="['flex', 'mt-1', 'ml-auto', 'w-fit', 'items-center', 'rounded-full', 'py-2', 'px-3', 'text-left', 'text-xs', 'font-medium', 'text-white', 'lg:hidden', getStatusClass(order.status)]">
                {{ order.status }}
              </div>
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-500 sm:px-6 lg:table-cell">
              <div :class="['inline-flex', 'items-center', 'rounded-full', 'py-2', 'px-3', 'text-xs', 'text-white', getStatusClass(order.status)]">
                {{ order.status }}
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
