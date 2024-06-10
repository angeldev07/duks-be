FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/dukscoffee-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "dukscoffee-0.0.1-SNAPSHOT.jar"]