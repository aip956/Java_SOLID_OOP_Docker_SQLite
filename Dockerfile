
# Use the official Java 11 image as the base image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the compiled Java file (and any required resources) from your local machine to the container

COPY . .

# Compile the application with all necessary dependencies
RUN javac -cp ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/slf4j-api-1.7.32.jar" MyMastermind.java Game.java Player.java SecretKeeper.java Guesser.java GameData.java GameDataDAO.java SQLiteGameDataDAO.java

# Command to run the application
CMD ["java", "-cp", ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/logback-classic-1.2.6.jar:lib/logback-core-1.2.6.jar:lib/slf4j-api-1.7.32.jar", "MyMastermind"]


# Build by typing: docker-compose up --build
# Run by typing: docker run -it --rm my-mastermind