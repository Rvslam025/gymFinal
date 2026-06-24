# Build from the repository root, for example:
# docker build -f gateway-service/Dockerfile --build-arg SERVICE_DIR=gateway-service -t gym-gateway-service .

FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /workspace

ARG SERVICE_DIR=gateway-service

COPY pom.xml ./pom.xml
COPY asistencia-service/pom.xml asistencia-service/pom.xml
COPY clase-service/pom.xml clase-service/pom.xml
COPY cliente-service/pom.xml cliente-service/pom.xml
COPY entrenador-service/pom.xml entrenador-service/pom.xml
COPY gateway-service/pom.xml gateway-service/pom.xml
COPY membresia-service/pom.xml membresia-service/pom.xml
COPY pago-service/pom.xml pago-service/pom.xml
COPY producto-service/pom.xml producto-service/pom.xml
COPY reserva-service/pom.xml reserva-service/pom.xml
COPY tipomembresia-Service/pom.xml tipomembresia-Service/pom.xml
COPY venta-service/pom.xml venta-service/pom.xml

RUN mvn -B -pl ${SERVICE_DIR} -am dependency:go-offline || true

COPY . .

RUN mvn -B -pl ${SERVICE_DIR} -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ARG SERVICE_DIR=gateway-service

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=builder /workspace/${SERVICE_DIR}/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
