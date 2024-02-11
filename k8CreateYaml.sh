#! /bin/bash

# Set color
YELLOW='\033[1;33m'
# reset color
NC='\033[0m'

COMPOSE_FILE="compose.yml"

# Check if kompose is installed
if ! command -v kompose &> /dev/null
then
    echo -e "${YELLOW}Kompose isn't installed.${NC}"
    exit 1
fi

kompose convert -f "$COMPOSE_FILE"

echo "# Deployments" > all-deployments.yaml
echo "# Services" > all-services.yaml
echo "# Persistent Volume Claims" > all-pvcs.yaml

for f in *-deployment.yaml; do
  (echo "---"; cat "${f}") >> all-deployments.yaml
  rm -f "${f}"
done

for f in *-service.yaml; do
  (echo "---"; cat "${f}") >> all-services.yaml
  rm -f "${f}"
done

for f in *-persistentvolumeclaim.yaml; do
  (echo "---"; cat "${f}") >> all-pvcs.yaml
  rm -f "${f}"
done

echo -e "${YELLOW}Creation of all-deployments.yaml, all-services.yaml, and all-pvcs.yaml completed.${NC}"
