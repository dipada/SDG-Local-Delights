FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app.jar"]