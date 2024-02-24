#!/bin/bash

# Endpoint per l'aggiunta di un negozio
URL="http://localhost:8082/shop/add"

# Funzione per aggiungere un negozio
add_shop() {
    curl -X POST "$URL" \
         -H "Content-Type: application/json" \
         -d '{
            "name": "'"$1"'",
            "description": "'"$2"'",
            "address": "'"$3"'",
            "phoneNumber": "'"$4"'",
            "email": "'"$5"'",
            "sellerEmail": "'"$6"'",
            "longitude": "'"$7"'",
            "latitude": "'"$8"'",
            "imageUrl": "'"$9"'"
         }'
}

# Chiamata della funzione add_shop per ogni negozio
# I valori qui sono solo esempi. Sostituiscili con i dati reali dei tuoi negozi.
add_shop "Negozio A" "Descrizione del Negozio A" "Indirizzo A, Torino" "0123456789" "emaila@negozio.it" "venditorea@negozio.it" "7.6868" "45.0703" "http://example.com/imageA.jpg"

add_shop "Negozio B" "Descrizione del Negozio B" "Indirizzo B, Torino" "9876543210" "emailb@negozio.it" "venditoreb@negozio.it" "7.6968" "45.0763" "http://example.com/imageB.jpg"

add_shop "Negozio C" "Descrizione del Negozio C" "Indirizzo C, Torino" "1234567890" "emailc@negozio.it" "venditorec@negozio.it" "7.7068" "45.0823" "http://example.com/imageC.jpg"

echo "I negozi sono stati aggiunti con successo."
