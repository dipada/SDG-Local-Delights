#!/bin/bash

# Percorso alla directory contenente i file YAML
DIRECTORY="./k8s"

# Applica tutti i file YAML nella directory specificata
for FILE in $DIRECTORY/*.yaml; do
    echo "Applicando $FILE..."
    kubectl apply -f $FILE
done

echo "Tutte le risorse sono state create."
