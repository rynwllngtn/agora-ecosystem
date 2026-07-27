FROM maven:3.9.14-eclipse-temurin-25-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY agora-common/pom.xml ./agora-common/
COPY agora-identity/pom.xml ./agora-identity/
COPY agora-bank/pom.xml ./agora-bank/

RUN mvn dependency:go-offline

COPY agora-common/src ./agora-common/src
COPY agora-bank/src ./agora-bank/src

RUN mvn clean package -DskipTests -pl agora-bank -am



FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=builder /app/agora-bank/target/*.jar agora-bank.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "agora-bank.jar"]