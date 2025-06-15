# Etapa de compilación
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Compilar (sin ejecutar pruebas)
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Etapa final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el jar generado
COPY --from=builder /app/target/back-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]