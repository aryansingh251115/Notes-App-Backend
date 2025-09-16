# Base JDK image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy jar file (replace with your jar name)
COPY target/*.jar app.jar

# Tell Heroku which port to use
ENV PORT=8080
EXPOSE $PORT

# Run the jar
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
