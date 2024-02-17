<template>
  <div>
    <input
        type="text"
        v-model="query"
        @input="cercaIndirizzo"
        placeholder="Inserisci un indirizzo..."
    />
    <ul v-if="indirizzi.length">
      <li v-for="(indirizzo, index) in indirizzi" :key="index" @click="selezionaIndirizzo(indirizzo)">
        {{ indirizzo.display_name }}
      </li>
    </ul>
    <button @click="saveAddress">Save</button>
    <div id="map" style="height: 500px;"></div>
  </div>
</template>

<script>
import L from 'leaflet';
import axios from 'axios';

export default {
  data() {
    return {
      query: '',
      indirizzi: [],
      savedAddresses: [],
      mappa: null,
      selectedAddress: null,
    };
  },
  mounted() {
    this.initMap();
  },
  methods: {
    cercaIndirizzo() {
      if (this.query.length > 2) {
        const url = `https://nominatim.openstreetmap.org/search?format=json&limit=5&q=${encodeURIComponent(this.query)}`;
        axios.get(url)
            .then(response => {
              this.indirizzi = response.data;
              console.log("Indirizzi salvati: ",this.indirizzi);
            })
            .catch(error => console.error('Errore nella ricerca degli indirizzi:', error));
      }
    },
    selezionaIndirizzo(indirizzo) {
      this.query = indirizzo.display_name;
      this.indirizzi = [];
      this.selectedAddress = indirizzo;
    },
    saveAddress() {
      if (this.selectedAddress) {
        this.savedAddresses.push(this.selectedAddress);
        this.updateMapMarkers();
        this.selectedAddress = null;
        this.query = '';
      }
    },
    updateMapMarkers() {
      this.mappa.eachLayer((layer) => {
        if (layer instanceof L.Marker) {
          this.mappa.removeLayer(layer); // Rimuovi i marker esistenti
        }
      });
      this.savedAddresses.forEach((address) => {
        L.marker([address.lat, address.lon])
            .addTo(this.mappa)
            .bindPopup(address.display_name);
      });
    },
    initMap() {
      this.mappa = L.map('map').setView([45.0703, 7.6869], 13);
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
      }).addTo(this.mappa);
      this.updateMapMarkers();
    },
  },
};
</script>