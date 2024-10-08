FROM openjdk:21
LABEL authors="AbdelZidane"

#ENTRYPOINT ["top", "-b"]

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/mailing-docker.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]