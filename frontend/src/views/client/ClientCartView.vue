<template>
  <HeaderBase>
    <template #nav>
      <div class="flex lg:flex-1 justify-end">
        <div class="flex items-center gap-4">
          <AvatarComponent class="pr-4" :image-url="userInfo.picture" :name="userInfo.name"/>
          <logout-button-component/>
        </div>
      </div>
    </template>
  </HeaderBase>

  <section class="py-24 font-poppins dark:bg-gray-700">
    <div class="px-4 py-6 mx-auto max-w-7xl lg:py-4 md:px-6">
      <div class="text-black">
        <h2 v-if="cartItems && cartItems.length" class="mb-8 text-4xl font-bold dark:text-gray-400">Your Cart</h2>
        <h2 v-else class="mb-8 text-4xl font-bold dark:text-gray-400">Empty cart :(</h2>
        <div v-if="cartItems && cartItems.length"
             class="p-6 mb-8 border bg-gray-50 dark:bg-gray-800 dark:border-gray-800">
          <div class="flex-wrap items-center hidden mb-6 -mx-4 md:flex md:mb-8">
            <div class="w-full px-4 mb-6 md:w-4/6 lg:w-6/12 md:mb-0">
              <h2 class="font-bold text-gray-500 dark:text-gray-400">Product name</h2>
            </div>
            <div class="hidden px-4 lg:block lg:w-2/12">
              <h2 class="font-bold text-gray-500 dark:text-gray-400">Price</h2>
            </div>
            <div class="w-auto px-4 md:w-1/6 lg:w-2/12 ">
              <h2 class="font-bold text-gray-500 dark:text-gray-400">Quantity</h2>
            </div>
            <div class="w-auto px-4 text-right md:w-1/6 lg:w-2/12 ">
              <h2 class="font-bold text-gray-500 dark:text-gray-400">Subtotal</h2>
            </div>
          </div>
          <div class="py-4 mb-8 border-t border-b border-gray-200 dark:border-gray-700">
            <div v-for="(item, index) in cartItemsDetails" :key="index"
                 class="flex flex-wrap items-center mb-6 -mx-4 md:mb-8">
              <div class="w-full px-4 mb-6 md:w-4/6 lg:w-6/12 md:mb-0">
                <div class="flex flex-wrap items-center -mx-4">
                  <div class="w-full px-4 mb-3 md:w-1/3">
                    <div class="w-full h-96 md:h-24 md:w-24">
                      <img :src="item.image" alt="" class="object-cover w-full h-full">
                    </div>
                  </div>
                  <div class="w-2/3 px-4">
                    <h2 class="mb-2 text-xl font-bold dark:text-gray-400">{{ item.name }}</h2>
                    <p class="text-gray-500 dark:text-gray-400">{{ item.description }}</p>
                  </div>
                </div>
              </div>
              <div class="hidden px-4 lg:block lg:w-2/12">
                <p class="text-lg font-bold text-secondary dark:text-gray-400">€{{ item.price.toFixed(2) }}</p>
              </div>
              <div class="w-auto px-4 md:w-1/6 lg:w-2/12 ">
                <div
                    class="inline-flex items-center px-4 font-semibold text-gray-500 border border-gray-200 rounded-md dark:border-gray-700">
                  <button class="py-2 hover:text-gray-700 dark:text-gray-400" @click="decrementQuantity(item.id)">
                    <svg width="20" height="20px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M6 12L18 12" stroke="#000000" stroke-width="2" stroke-linecap="round"
                            stroke-linejoin="round"/>
                    </svg>
                    <!-- Icon for decrement -->
                  </button>
                  <input type="number" readonly
                         class="w-12 px-2 py-4 text-center border-0 rounded-md dark:bg-gray-800 bg-gray-50 dark:text-gray-400 md:text-right"
                         v-model.number="item.quantity">
                  <button class="py-2 hover:text-gray-700 dark:text-gray-400" @click="incrementQuantity(item.id)">
                    <svg width="20px" height="20px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M6 12H18M12 6V18" stroke="#000000" stroke-width="2" stroke-linecap="round"
                            stroke-linejoin="round"/>
                    </svg>
                  </button>
                </div>
              </div>
              <div class="w-auto px-4 text-right md:w-1/6 lg:w-2/12 ">
                <p class="text-lg font-bold text-secondary dark:text-gray-400">€{{ calculateSubtotal(item) }}</p>
              </div>
            </div>
          </div>
        </div>

        <div v-if="cartItemsDetails && cartItemsDetails.length" class="flex flex-wrap justify-between">

          <div class="w-full px-4 mb-4 lg:w-1/2">
            <div class="flex flex-wrap items-center gap-4">
              <span class="text-green-800 dark:text-gray-400">Apply Coupon</span>
              <input type="text"
                     class="w-full px-8 py-4 font-normal placeholder-gray-400 border lg:flex-1 dark:border-gray-700 dark:placeholder-gray-500 dark:text-gray-400 dark:bg-gray-800"
                     placeholder="x304k45">
              <button
                  class="inline-block w-full px-8 py-4 font-bold text-center text-gray-100 bg-secondary rounded-md lg:w-32 hover:bg-green-800">
                Apply
              </button>
            </div>
          </div>
          <div class="w-full px-4 mb-4 lg:w-1/2">
            <div class="p-6 border border-green-800 dark:bg-gray-900 dark:border-gray-900 bg-gray-50 md:p-8">
              <h2 class="mb-8 text-3xl font-bold text-gray-700 dark:text-gray-400">Order Summary</h2>
              <div class="flex items-center justify-between pb-4 mb-4 border-b border-gray-300 dark:border-gray-700">
                <span class="text-gray-700 dark:text-gray-400">Subtotal</span>
                <span class="text-xl font-bold text-gray-700 dark:text-gray-400">€{{ orderTotal.toFixed(2) }}</span>
              </div>
              <div class="flex items-center justify-between pb-4 mb-4">
                <span class="text-gray-700 dark:text-gray-400">Shipping</span>
                <span class="text-xl font-bold text-gray-700 dark:text-gray-400">Free</span>
              </div>
              <div class="flex items-center justify-between pb-4 mb-4">
                <span class="text-gray-700 dark:text-gray-400">Order Total</span>
                <span class="text-xl font-bold text-gray-700 dark:text-gray-400">€{{ orderTotal.toFixed(2) }}</span>
              </div>

              <h2 class="text-lg text-gray-500 dark:text-gray-400">We offer:</h2>
              <div class="flex items-center gap-2 mb-4 ">
                <a href="#">
                  <img src="https://i.postimg.cc/g22HQhX0/70599-visa-curved-icon.png" alt=""
                       class="object-cover h-16 w-26">
                </a>
                <a href="#">
                  <img src="https://i.postimg.cc/HW38JkkG/38602-mastercard-curved-icon.png" alt=""
                       class="object-cover h-16 w-26">
                </a>
                <a href="#">
                  <img src="https://i.postimg.cc/HL57j0V3/38605-paypal-straight-icon.png" alt=""
                       class="object-cover h-16 w-26">
                </a>
              </div>
              <div class="flex items-center justify-between mx-auto mb-2"><b>Your balance:</b> {{ money }}</div>
              <div class="flex items-center justify-between mx-auto mb-2" :class="{'text-red-500': !canCheckoutWallet}"><b>Your
                balance after order:</b> {{ money - this.orderTotal }}
              </div>
              <div class="mx-auto mb-2">
                <label :class="{'text-red-500': !canCheckoutAddress}"><b>Shipping address</b></label>
                <input @input="searchAddress" v-model="query" type="text" placeholder="Via roma 1" required
                       class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
                <div v-if="indirizzi.length" class="relative w-full mt-1 text-secondary">
                  <ul class="bg-white border border-gray-300 rounded-lg text-sm">
                    <li v-for="(adr, index) in indirizzi" :key="index"
                        @click="selectAddress(adr)"
                        class="p-2 hover:bg-gray-100 cursor-pointer">
                      {{ adr.display_name }}
                    </li>
                  </ul>
                </div>
              </div>
              <div class="flex items-center justify-between ">
                <button @click="makeCheckout" :disabled="!canCheckout"
                        class="block w-full py-4 font-bold text-center text-gray-100 uppercase rounded-md"
                        :class="{'bg-secondary hover:bg-green-800': canCheckout, 'bg-gray-400 cursor-not-allowed': !canCheckout}">
                  Checkout
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

</template>

<script>
import HeaderBase from "@/components/HeaderBase.vue";
import axios from "axios";
import store from "@/store/index.js";
import AvatarComponent from "@/components/avatarComponent.vue";
import LogoutButtonComponent from "@/components/LogoutButtonComponent.vue";

export default {
  name: 'ClientCartView',
  components: {LogoutButtonComponent, AvatarComponent, HeaderBase},
  data() {
    return {
      cartItemsDetails: [],
      money: null,
      indirizzi: [],
      query: '',
      addressSelected: false
    };
  },
  computed: {
    canCheckoutWallet() {
      return (this.money - this.orderTotal >= 0);
    },

    canCheckoutAddress(){
      return this.query.length > 2 && this.addressSelected
    },

    canCheckout(){
      return this.canCheckoutWallet && this.canCheckoutAddress
    },

    store() {
      return store
    },
    cartItems() {
      return this.$store.getters.getCartProducts || [];
    },
    orderTotal() {
      return this.cartItemsDetails.reduce((total, item) => total + (item.price * item.quantity), 0);
    },
    userInfo() {
      return this.$store.getters.getUserInfo;
    }
  },

  methods: {
    selectAddress(address) {
      this.query = address.display_name;
      this.indirizzi = [];
      this.addressSelected = true;
    },


    searchAddress() {
      clearTimeout(this.searchTimeout);
      this.searchTimeout = setTimeout(() => {
        if (this.query.length > 2) {
          this.addressSelected = false;
          const url = `https://nominatim.openstreetmap.org/search?format=json&limit=5&q=${encodeURIComponent(this.query)}`; // Usa this.query
          axios.get(url)
              .then(response => {
                this.indirizzi = response.data;
              })
              .catch(error => {
                console.error('Error fetching address:', error);
              });
        } else {
          this.indirizzi = [];
        }
      }, 500);
    },


    async makeCheckout() {
      const email = this.$store.state.userInfo.email;
      const shopId = this.$store.state.shopId;
      let orderProductsIds = [];
      this.$store.state.cartProducts.forEach(product => {
        for (let i = 0; i < product.quantity; i++) {
          orderProductsIds.push(product.id);
        }
      });
      const amount = this.orderTotal;
      const orderResponse = await axios.post('http://localhost:8085/api/v1/order/create-order', {
        userEmail: email,
        shopId: shopId,
        listOfProductIds: orderProductsIds,
        amount: amount,
        shippingAddress: this.query
      }, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      });

      const orderId = orderResponse.data;

      axios.post('http://localhost:8085/payment/pay', {
        email: email,
        amount: amount,
        orderId: orderId
      }, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      }).then(response => {
        console.log("Pagamento effettuato con successo: ", response);
        this.$store.dispatch('emptyCart');
        this.$router.push('/client/home');
      }).catch(error => {
        console.log("Errore nel pagamento: ", error);
      });
    },

    fetchUserBalance() {
      axios.get(`http://localhost:8085/payment/balance/${this.userInfo.email}`, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      })
          .then(response => {
            console.log("User balance: ", response.data);
            this.money = response.data;
          })
          .catch(error => {
            console.log(error);
          });
    },

    calculateSubtotal(item) {
      return item.price * item.quantity;
    },

    incrementQuantity(productId) {

      this.$store.commit('addProductToCart', productId);
      this.fetchCartItemsDetails();
    },

    decrementQuantity(productId) {
      this.$store.commit('removeProductFromCart', productId);
      this.fetchCartItemsDetails();
    },

    async fetchCartItemsDetails() {
      try {
        const requests = this.cartItems.map(item =>
            axios.get(`http://localhost:8085/product/get/${item.id}`, {
              headers: {
                'Authorization': 'Bearer ' + store.getters.getUserToken,
                'Accept': '*/*'
              },
            }).then(response => {
              // bind the quantity of the item in the cart to the response
              return {...response.data, quantity: item.quantity};
            })
        );
        this.cartItemsDetails = await Promise.all(requests);
      } catch (error) {
        console.error('Errore nel recupero dei dettagli dei prodotti nel carrello:', error);
      }
    },
  },

  mounted() {
    this.fetchCartItemsDetails();
    this.fetchUserBalance();
  }
};
</script>
