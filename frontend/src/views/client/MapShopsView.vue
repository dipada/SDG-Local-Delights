<template>
  <div id="map" style="height: 500px;"></div>
</template>

<script>
import L from 'leaflet';
import router from '@/router';
import axios from "axios";
import store from "@/store/index.js"; // Assicurati che il percorso sia corretto

// set the default authorization header
//axios.interceptors.request.use(config => {
 // config.headers.Authorization = `Bearer ${store.getters.getUserToken}`;
 // return config;
//});

export default {
  name: 'MapShopsView',
  data() {
    return {
      markers: []
    };
  },
  created() {
    axios.get("http://localhost:8085/shop/all",{
      headers: {
        'Authorization': 'Bearer ' + store.getters.getUserToken,
        'Accept': '*/*'
        // Nota: Non è necessario impostare l'header 'Host' e 'Connection' in axios, verranno gestiti automaticamente
      },
    })
        .then(response => {
          console.log(response.data);
          this.markers = response.data.map(shop => {
            return {
              id: shop.id,
              lat: shop.latitude,
              lng: shop.longitude,
              name: shop.name,
              image: shop.imageUrl
            };
          });

          console.log(this.markers);

          this.initMap();
        })
        .catch(error => {
          console.log("Errore " + error);
        });
  },
  methods: {
    initMap() {
      const map = L.map('map').setView([45.0703, 7.6869], 13);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
      }).addTo(map);

      const markers2 = [
        //{id: 1, lat: 45.074512, lng: 7.694419, name: 'Negozio A', image: 'https://www.grupposmau.com/wp-content/uploads/2021/07/arredare-negozio-abbigliamento.jpg'},
        //{id: 2, lat: 45.067255, lng: 7.682489, name: 'Negozio B', image: 'https://flawless.life/wp-content/uploads/2020/08/Le-Boutique-Chic-di-Torino-cover.jpg'},
        // Altri negozi...
      ];

      this.markers.forEach(marker => {
        const popupContent = `
          <div class="w-60">
            <b><a href="#" onclick="event.preventDefault(); routerPush(${marker.id})">${marker.name}</a></b>
            <br>
            <img src="${marker.image}" alt="${marker.name}" style="width:300px; height:auto;">
          </div>
        `;

        L.marker([marker.lat, marker.lng])
            .addTo(map)
            .bindPopup(popupContent);
      });

      window.routerPush = (id) => {
        router.push(`/client/shop/${id}`);
      };
    }
  },
  beforeDestroy() {
    delete window.routerPush;
  }
};
</script>

<style>
/* Stili aggiuntivi per la mappa, se necessario */
</style>
