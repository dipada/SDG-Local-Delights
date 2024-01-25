<template>
  <div id="map" style="height: 500px;"></div>
</template>

<script>
import L from 'leaflet';

export default {
  name: 'MapShopsView',
  mounted() {
    this.initMap();
  },
  methods: {
    initMap() {
      const map = L.map('map').setView([45.0703, 7.6869], 13); // Coordinate centrali di Torino

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
      }).addTo(map);

      // Aggiunta dei marker
      const markers = [
        { lat: 45.074512, lng: 7.694419, name: 'Negozio A' },
        { lat: 45.067255, lng: 7.682489, name: 'Negozio B' },
        { lat: 45.071123, lng: 7.678255, name: 'Negozio C' },
        { lat: 45.076233, lng: 7.689877, name: 'Negozio D' },
        { lat: 45.073200, lng: 7.678900, name: 'Negozio E' },
      ];

      markers.forEach(marker => {
        L.marker([marker.lat, marker.lng])
            .addTo(map)
            .bindPopup(marker.name);
      });
    }
  }
};
</script>

<style>
/* Stili aggiuntivi per la mappa, se necessario */
</style>
