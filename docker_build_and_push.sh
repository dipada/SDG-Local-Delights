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
    "discoveryserver:./discoveryserver"
    "frontend:./frontend"
    "orderservice:./orderservice"
    "paymentservice:./paymentservice"
    "productservice:./productservice"
    "shopservice:./shopservice"
    "userservice:./userservice"
)

# Build images in parallel
for IMAGE_INFO in "${IMAGES[@]}"; do
    IFS=":" read -r IMAGE_NAME IMAGE_DIR <<< "${IMAGE_INFO}"
    TAG="latest"

    echo -e "${YELLOW}Building ${IMAGE_NAME} from ${IMAGE_DIR}${NC}"
    docker build --no-cache -t "${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}" "${IMAGE_DIR}" &
done

wait
echo -e "${YELLOW}All images have been build${NC}"

#Push is sequential
for IMAGE_INFO in "${IMAGES[@]}"; do
    IFS=":" read -r IMAGE_NAME IMAGE_DIR <<< "${IMAGE_INFO}"
    TAG="latest"

    echo -e "${YELLOW}Pushing ${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}${NC}"
    docker push "${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}" || exit 1
done

echo -e "${YELLOW}All images have been built and pushed successfully.${NC}"