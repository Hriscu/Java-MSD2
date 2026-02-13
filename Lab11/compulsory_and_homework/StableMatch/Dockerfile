# 1. Use a lightweight base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Define an argument for the JAR file location
ARG JAR_FILE=target/*.jar

# 4. Copy the JAR file from your machine to the container
COPY ${JAR_FILE} app.jar

# 5. Expose the port your application runs on (check application.properties, usually 8080)
EXPOSE 8080

# 6. The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]