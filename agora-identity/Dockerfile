FROM maven:3.9.14-eclipse-temurin-25-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY agora-common/pom.xml ./agora-common/
COPY agora-identity/pom.xml ./agora-identity/

RUN mvn dependency:go-offline

COPY agora-common/src ./agora-common/src
COPY agora-identity/src ./agora-identity/src

RUN mvn clean package -DskipTests -pl agora-identity -am



FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=builder /app/agora-identity/target/*.jar agora-identity.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "agora-identity.jar"]