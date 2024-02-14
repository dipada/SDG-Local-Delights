#! /bin/bash

# Set color
YELLOW='\033[1;33m'
# reset color
NC='\033[0m'

# dockerhub username
DOCKER_USERNAME="dipada"

# List of images to build and push. Format: "image_name:directory"
IMAGES=(
    "apigateway:./apigateway"
    "authenticationservice:./authenticationservice"
    "deliveryservice:./deliveryservice"
    "discoveryserver:./discoveryserver"
    "orderservice:./orderservice"
    "paymentservice:./paymentservice"
    "productservice:./productservice"
    "shopservice:./shopservice"
    "userservice:./userservice"
    "frontend:./frontend"
)

# Loop through each image
for IMAGE_INFO in "${IMAGES[@]}"; do
    # Split IMAGE_INFO into image name and directory
    IFS=":" read -r IMAGE_NAME IMAGE_DIR <<< "${IMAGE_INFO}"
    TAG="latest"

    echo -e "${YELLOW} Building ${IMAGE_NAME} from ${IMAGE_DIR} ${NC}"
    #echo "Building ${IMAGE_NAME} from ${IMAGE_DIR}"
    docker build -t "${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}" "${IMAGE_DIR}" || exit 1

    echo -e "${YELLOW} Pushing ${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG} ${NC}"
    #echo "Pushing ${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}"
    docker push "${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}" || exit 1
done

echo "All images have been built and pushed successfully."
