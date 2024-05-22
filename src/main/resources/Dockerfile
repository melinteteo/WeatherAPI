FROM openjdk:17-jdk-alpine
LABEL authors="melinteteo5@gmail.com"
ARG JAR_FILE=target/*.jar
COPY ./target/Weather-0.0.1-SNAPSHOT.jar weatherApp.jar
ENTRYPOINT ["java","-jar","/weatherApp.jar"]
