#!/bin/bash

# dockerhub username
DOCKER_USERNAME="dipada"

# List of images to build and push. Format: "image_name:directory"
IMAGES=(
    "authenticationservice:./authenticationservice"
    "userservice:./userservice"
)

# Loop through each image
for IMAGE_INFO in "${IMAGES[@]}"; do
    # Split IMAGE_INFO into image name and directory
    IFS=":" read -r IMAGE_NAME IMAGE_DIR <<< "${IMAGE_INFO}"
    TAG="latest"

    echo "Building ${IMAGE_NAME} from ${IMAGE_DIR}"
    docker build -t "${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}" "${IMAGE_DIR}" || exit 1

    echo "Pushing ${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}"
    docker push "${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}" || exit 1
done

echo "All images have been built and pushed successfully."
