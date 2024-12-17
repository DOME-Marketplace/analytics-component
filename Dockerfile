
FROM maven:3.8.3-openjdk-17 AS builder
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17
COPY --from=builder target/matomo_analytics-0.0.1-SNAPSHOT.jar runner.jar
CMD ["java", "-jar", "runner.jar"]
