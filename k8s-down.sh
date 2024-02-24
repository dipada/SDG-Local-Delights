#! /bin/bash

# set color
YELLOW='\033[1;33m'
# reset color
NC='\033[0m'

echo -e "${YELLOW}Deleting deployments${NC}"
kubectl delete -f all-deployments.yaml
echo -e "${YELLOW}Deleting services${NC}"
kubectl delete -f all-services.yaml


echo -e "${YELLOW}All files have been deleted.${NC}"
