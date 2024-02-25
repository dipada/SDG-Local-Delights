FROM maven:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -U

FROM openjdk:21
LABEL authors="dipada"
COPY --from=build /usr/src/app/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app.jar"]