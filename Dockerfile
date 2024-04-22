
# Use the official Java 11 image as the base image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /usr/src/app

# Install SQLite
RUN apt-get update && apt-get install -y sqlite3 && apt-get install -y wget

# Download all the .jar files dependencies
RUN wget https://repo1.maven.org/maven2/ch/qos/logback/logback-classic/1.2.6/logback-classic-1.2.6.jar -P ./src/lib
RUN wget https://repo1.maven.org/maven2/ch/qos/logback/logback-core/1.2.6/logback-core-1.2.6.jar -P ./src/lib 
RUN wget https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.45.3.0/sqlite-jdbc-3.45.3.0.jar -P ./src/lib
RUN wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar -P ./src/lib

# Copy the compiled Java file (and any required resources) from local machine to the container
COPY . .

# List to confirm all was copied
RUN ls -la /usr/src/app/src

# Compile the application with all necessary dependencies
RUN javac -cp ".:src/lib/*" ./src/DAO/*.java ./src/DBConnectionManager/*.java ./src/Models/*.java ./src/View/*.java ./src/MyMastermind.java

# Will run the build
CMD ["java", "-cp", ".:src/lib/*:src/", "MyMastermind"]


