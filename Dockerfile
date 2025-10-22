FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN cd back/apiEleves && mvn clean package -DskipTests

RUN echo "=== Fichiers dans target/ ==="
RUN ls -la /app/back/apiEleves/target/
RUN echo "=== Fin liste ==="

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/back/apiEleves/target/app.war app.war

RUN echo "=== Fichiers dans /app/ ==="
RUN ls -la
RUN echo "=== Fin liste ==="

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]