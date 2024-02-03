<template>
  <div id="map" style="height: 500px;"></div>
</template>

<script>
import L from 'leaflet';
import router from '@/router';
import axios from "axios";
import store from "@/store/index.js";

export default {
  name: 'MapShopsView',
  data() {
    return {
      markers: []
    };
  },
  created() {
    axios.get("http://localhost:8085/shop/all", {
      headers: {
        'Authorization': 'Bearer ' + store.getters.getUserToken,
        'Accept': '*/*'
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

      this.markers.forEach(marker => {
        const popupContent = `
          <div class="w-60">
            <a href="#" onclick="event.preventDefault(); routerPush(${marker.id})"><b>${marker.name}</b>
            <br>
            <img src="${marker.image}" alt="${marker.name}" style="width:300px; height:auto;"></a>
          </div>
        `;

        L.marker([marker.lat, marker.lng])
            .addTo(map)
            .bindPopup(popupContent);
      });

      window.routerPush = (id) => {
        store.commit('setShopId', id);
        router.push('/client/shop');
      };
    },
  },
  beforeDestroy() {
    delete window.routerPush;
  }
};
</script>