
# Use the official Java 11 image as the base image
FROM openjdk:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java file (and any required resources) from your local machine to the container

COPY MyMastermind.java .
COPY GameState.java .

# Compile the Java source file
RUN javac MyMastermind.java GameState.java


# Run the Java application inside the container

ENTRYPOINT  ["java", "-cp", "/app", "MyMastermind"]
CMD [ "-c", "0123", "-t", "10"]


# Build by typing: docker build -t my_mastermind.image .
# Run by typing: docker run my_mastermind.image