FROM eclipse-temurin:25-alpine AS build
WORKDIR /app

ADD target/children-budget-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
