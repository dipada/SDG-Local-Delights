#!/bin/bash

# Set color
YELLOW='\033[1;33m'
# Reset color
NC='\033[0m'

COMPOSE_FILE="compose.yml"

# Check if Kompose is installed
if ! command -v kompose &> /dev/null
then
    echo -e "${YELLOW}Kompose isn't installed. Please install it and try again.${NC}"
    exit 1
fi

kompose convert -f "$COMPOSE_FILE"

# Function to fix service names to comply with DNS-1035
fix_service_names() {
    sed -i 's/[-_]\([^-_]*\):/.\1:/g' $1
    sed -i 's/\([^-_a-z0-9]\)/-/g' $1
    sed -i 's/^[^a-z]*/service-/g' $1
}

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

# Apply the fix to the combined YAML files
fix_service_names all-deployments.yaml
fix_service_names all-services.yaml
fix_service_names all-pvcs.yaml

echo -e "${YELLOW}Creation of all-deployments.yaml, all-services.yaml, and all-pvcs.yaml completed.${NC}"
echo -e "${YELLOW}Service names have been fixed to comply with DNS-1035.${NC}"
