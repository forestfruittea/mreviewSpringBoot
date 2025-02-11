# Use a base image that has JDK
FROM openjdk:21-jdk-slim

# Copy the jar file from the target folder into the image
COPY target/springBootMovie-0.0.1-SNAPSHOT.jar /app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
