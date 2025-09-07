# -------- Build stage: build the Spring Boot JAR with Maven --------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper & sources (wrapper keeps the build reproducible)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Build the application (skip tests to speed up container builds)
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

RUN ./mvnw -B -DskipTests clean package

# -------- Run stage: run the built JAR on a slim JRE image --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Use prod profile in container
ENV SPRING_PROFILES_ACTIVE=prod

# Container listens on 8080 (internal)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java","-jar","/app/app.jar"]