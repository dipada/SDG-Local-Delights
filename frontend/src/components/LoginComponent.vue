<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8 rounded-3xl">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-green-800">Login</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form class="space-y-6" action="#" method="POST">
        <div>
          <label for="email" class="block text-sm font-medium leading-6 text-green-800">Email address</label>
          <div class="mt-2">
            <input id="email" name="email" type="email" autocomplete="email" required="" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-secondary sm:text-sm sm:leading-6" />
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
            <input id="password" name="password" type="password" autocomplete="current-password" required="" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-secondary sm:text-sm sm:leading-6" />
          </div>
        </div>

        <div>
          <button type="submit" class="flex w-full justify-center rounded-md bg-secondary px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-green-900 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Login</button>
        </div>
        <div class="seperator">
          <h5><span>oppure</span></h5>
        </div>
      </form>
      <div class="mt-4">
        <button type="submit" class="flex w-full justify-center rounded-md bg-white px-3 py-1.5 text-sm font-semibold leading-6 text-secondary shadow-sm hover:bg-secondary hover:text-white focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600" @click="googleLogin">
          <img src="../assets/google.png" alt="google" class="w-5 h-5 me-2">
          Log in with Google
        </button>
      </div>

    </div>
  </div>
</template>
<script setup>
import axios from 'axios';

// Interceptor per le richieste
axios.interceptors.request.use(request => {
  console.log('Starting Request', JSON.stringify(request, null, 2));
  return request;
});

// Interceptor per le risposte
axios.interceptors.response.use(response => {
  console.log('Response:', JSON.stringify(response, null, 2));
  return response;
});

// Funzione per eseguire la chiamata GET
const googleLogin = () => {
  axios.get('http://localhost:8081/api/v1/user/client/' + 'dani@gmail.com')
      .then(response => {
        console.log('Risposta:', response);
        console.log('Dati:', response.data);
      })
      .catch(error => {
        console.error('Errore nella chiamata API:', error);
      });
};

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