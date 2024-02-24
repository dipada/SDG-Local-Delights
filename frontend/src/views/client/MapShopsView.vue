<template>
  <div id="map" style="justify-content: center; width: 65%; height: 500px;"></div>
</template>

<style>
  #map {
    margin: 0 auto;
    border: 1px solid green;
  }
</style>

<script>
import L from 'leaflet';
import router from '@/router';
import axios from "axios";
import store from "@/store/index.js";

export default {
  name: 'MapShopsView',
  data() {
    return {
      map: null,
      markers: [],
    };
  },
  created() {
    this.fetchShops();
  },
  methods: {
    fetchShops() {
      axios.get("http://localhost:30085/shop/all", {
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*',
        },
      })
          .then(response => {
            this.markers = response.data.map(shop => ({
              id: shop.id,
              lat: shop.latitude,
              lng: shop.longitude,
              name: shop.name,
              image: shop.imageUrl,
            }));

            if (!this.map) {
              this.initMap();
            }
            this.addMarkersToMap();
          })
          .catch(error => {
            console.log("Errore " + error);
          });
    },
    initMap() {
      const defaultCoords = [45.0703, 7.6869];
      this.map = L.map('map').setView(defaultCoords, 13);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors',
      }).addTo(this.map);
    },
    addMarkersToMap() {
      this.markers.forEach(marker => {
        const popupContent = `
          <div class="w-60">
            <a href="#" onclick="event.preventDefault(); routerPush(${marker.id})">
              <b>${marker.name}</b>
              <br>
              <img src="${marker.image}" alt="${marker.name}" style="width:300px; height:auto;">
            </a>
          </div>
        `;

        L.marker([marker.lat, marker.lng])
            .addTo(this.map)
            .bindPopup(popupContent);
      });
    },
  },
  computed: {
    selectedCoordinates() {
      return this.$store.state.selectedCoordinates;
    },
  },
  watch: {
    selectedCoordinates: {
      deep: true,
      handler(newCoords) {
        if (newCoords.lat && newCoords.lon) {
          this.map.setView([newCoords.lat, newCoords.lon], 14);
        }
      }
    }
  },
  beforeDestroy() {
    if (this.map) {
      this.map.remove();
    }
  },
};

window.routerPush = (id) => {
  store.commit('setShopId', id);
  router.push('/client/shop');
};
</script>
