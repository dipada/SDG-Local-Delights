#!/bin/bash

# Percorso al file docker-compose.yml
COMPOSE_FILE="compose.yml"

# Verifica se Kompose è installato
if ! command -v kompose &> /dev/null
then
    echo "Kompose non è installato. Installalo e riprova."
    exit 1
fi

# Utilizza Kompose per convertire docker-compose.yml in file YAML di Kubernetes
kompose convert -f "$COMPOSE_FILE"

# Reset/Crea file combinati
echo "# Deployments" > all-deployments.yaml
echo "# Services" > all-services.yaml
echo "# Persistent Volume Claims" > all-pvcs.yaml

# Combina i Deployment
for f in *-deployment.yaml; do
  (echo "---"; cat "${f}") >> all-deployments.yaml
  rm -f "${f}" # Rimuovi il file dopo averlo unito
done

# Combina i Service
for f in *-service.yaml; do
  (echo "---"; cat "${f}") >> all-services.yaml
  rm -f "${f}" # Rimuovi il file dopo averlo unito
done

# Combina i PVCs
for f in *-persistentvolumeclaim.yaml; do
  (echo "---"; cat "${f}") >> all-pvcs.yaml
  rm -f "${f}" # Rimuovi il file dopo averlo unito
done

echo "Conversione e unificazione completate."
