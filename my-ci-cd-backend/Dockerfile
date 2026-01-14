FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . /workspace
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy
COPY --from=builder /workspace/target/my-ci-cd-backend-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
