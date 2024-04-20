# docker-compose up -d && docker attach game

#!/bin/bash

# Start the Docker container
docker-compose up -d

# Wait for the container to be up and running
echo "Waiting for container to start"
sleep 10

# Attach to the running container
docker attach game

# Wait for the game to finish (replace this with your actual game ending condition)
echo "Waiting for the game to finish..."
# For example, you can wait for a specific key press to end the game
read -p "Press any key to end the game..."

# After the game ends, stop the container
docker-compose down
