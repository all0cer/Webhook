#Maven
FROM maven:3.8.6-amazoncorretto-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean -e -B package

# Java
FROM openjdk:20-slim-buster

RUN mkdir target
COPY --from=builder /app/target/*.jar target/webhook-fluig.jar

EXPOSE 8081
CMD ["java","-jar","target/webhook-fluig.jar"]
