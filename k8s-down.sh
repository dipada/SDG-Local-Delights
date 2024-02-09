#!/bin/bash

# Percorso alla directory contenente i file YAML
DIRECTORY="./k8s"

# Elimina tutte le risorse definite nei file YAML
for FILE in $DIRECTORY/*.yaml; do
    echo "Eliminando le risorse definite in $FILE..."
    kubectl delete -f $FILE
done

echo "Tutte le risorse sono state eliminate."
