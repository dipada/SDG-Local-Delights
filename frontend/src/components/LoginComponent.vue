<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8 rounded-3xl">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-green-800">Login</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="customLogin" class="space-y-6" action="" method="POST">
        <div>
          <label for="email" class="block text-sm font-medium leading-6 text-green-800">Email address</label>
          <div class="mt-2">
            <input v-model="email" id="email" name="email" type="email" autocomplete="email" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-secondary sm:text-sm sm:leading-6"/>
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm font-medium leading-6 text-green-800">Password</label>
            <div class="text-sm">
              <a href="#" class="font-semibold text-secondary hover:text-green-900">Forgot password?</a>
            </div>
          </div>
          <div class="mt-2">
            <input v-model="password" id="password" name="password" type="password" autocomplete="current-password" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-secondary sm:text-sm sm:leading-6"/>
          </div>
          <div v-if="loginError" class="mt-2 text-sm text-red-600">
            {{ loginErrorMessage }}
          </div>
        </div>

        <div>
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-secondary px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-green-900 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  @submit="customLogin">Login
          </button>
        </div>
        <div class="seperator">
          <h5><span>oppure</span></h5>
        </div>
      </form>
      <div class="mt-4">
        <button type="submit"
                class="flex w-full justify-center rounded-md bg-white px-3 py-1.5 text-sm font-semibold leading-6 text-secondary shadow-sm hover:bg-secondary hover:text-white focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                @click="googleLogin">
          <img src="../assets/google.png" alt="google" class="w-5 h-5 me-2">
          Log in with Google
        </button>
      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {mapActions} from "vuex";

export default {
  name: "LoginComponent",

  data() {
    return {
      email: "",
      password: "",
      loginError: false,
      loginErrorMessage: ""
    }
  },

  methods: {

    ...mapActions(['saveUserInfo']),

    googleLogin(){
      window.location.href = 'http://localhost:30080/auth/google?redirect_uri=' + encodeURIComponent("http://localhost:5173/redirect/oauth");
    },

    customLogin() {

      if (this.email === "" || this.password === "") {
        alert("Please fill in all fields.");
        return;
      }

      axios.post('http://localhost:30080/auth/login', {
            email: this.email,
            password: this.password
          },
          {
            headers: {
              'Content-Type': 'application/json',
              'Accept': '*/*'
            }
          }
      ).then(async response => {
        this.loginError = false;
        this.loginErrorMessage = "";

        console.log("Token : ", JSON.stringify(response.data.token));
        await this.saveUserInfo(response.data.token);
        setTimeout(() => {
          this.isLoading = false;
          this.$router.push('/client/home');
        }, 1000);

      }).catch(error => {
        console.log("ERROR " , error);
        this.loginError = true;
        this.loginErrorMessage = "Error occurred during login. Server error code: " + error.response.data;
      });
    }
  }
}
</script>

<style scoped>
.seperator h5 {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 1em;
  color: #344e41;
}

.seperator h5::before,
.seperator h5::after {
  content: "";
  display: block;
  flex-grow: 1;
  height: 1px;
  background: #344e41;
}

.seperator h5 span {
  padding: 0 2em;
}
</style>