FROM eclipse-temurin:25.0.1_8-jre-alpine
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
