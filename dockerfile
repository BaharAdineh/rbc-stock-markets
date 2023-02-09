FROM openjdk:17-alpine
WORKDIR /app
COPY target/rbc-stock-markets-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]