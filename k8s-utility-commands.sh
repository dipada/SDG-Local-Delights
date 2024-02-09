#!/bin/bash

echo "Elenca tutti i pods..."
kubectl get pods

echo "Elenca tutti i servizi..."
kubectl get svc

echo "Elenca tutti i deployments..."
kubectl get deployments

echo "Elenca tutti i PersistentVolumeClaims..."
kubectl get pvc
