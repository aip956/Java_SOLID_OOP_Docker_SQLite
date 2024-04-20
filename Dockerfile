
# Use the official Java 11 image as the base image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /usr/src/app

# Install SQLite
RUN apt-get update && apt-get install -y sqlite3

# Copy the compiled Java file (and any required resources) from your local machine to the container
COPY . .

# List to confirm all was copied
RUN ls -la /usr/src/app

# Compile the application with all necessary dependencies
#RUN javac -cp ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/slf4j-api-1.7.32.jar" MyMastermind.java Game.java Player.java SecretKeeper.java Guesser.java GameData.java GameDataDAO.java SQLiteGameDataDAO.java
#RUN javac -cp ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/slf4j-api-1.7.32.jar"
RUN javac -cp ".:lib/*" MyMastermind.java Game.java Player.java SecretKeeper.java Guesser.java GameData.java GameDataDAO.java SQLiteGameDataDAO.java
#
# Command to run the application
#CMD ["java", "-cp", ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/logback-classic-1.2.6.jar:lib/logback-core-1.2.6.jar:lib/slf4j-api-1.7.32.jar", "MyMastermind"]
CMD ["java", "-cp", ".:lib/*", "MyMastermind"]

# Use this to build cont; Build multi container by typing: docker-compose up --build
# docker-compose down to stop and remove
# Run by typing: docker run -it --rm my-mastermind


# docker build -t javamm .

# Run the app in docker: docker run -it --rm javamm


# Enter the docker shell: docker run -it --rm javamm bash
# cd /usr/src/app/data
# cd data
# sqlite3 MM_Reach.db
# SELECT * FROM game_data;

# folder for types DAO, Models / classes instantiated in main, gamedata, game, player?
# MM outside
# repo/manager
# Viewer / gameUI
# Remove postgres traces

# Make running
# J-unity; methods have test class
# 
# scripts folder; nvim docker -compose and attach game
# # use attach; for termina'ls standard input
# docker exec -it number /bin/bash
# docker ps to get container number
# Stress test to determine db; mockito fake data
# windows test