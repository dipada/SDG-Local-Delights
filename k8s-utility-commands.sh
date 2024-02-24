#! /bin/bash

# Set color
YELLOW='\033[1;33m'
# Reset color
NC='\033[0m'

echo -e "${YELLOW}Pods${NC}"
kubectl get pods

echo -e "${YELLOW}Services${NC}"
kubectl get svc

echo -e "${YELLOW}Deployments${NC}"
kubectl get deployments

echo -e "${YELLOW}Persistent Volume Claims${NC}"
kubectl get pvc