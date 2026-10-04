FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package dependency:copy-dependencies \
    -DincludeScope=runtime \
    -DoutputDirectory=target/dependency


FROM eclipse-temurin:21-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgtk-3-0 \
    mesa-utils \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/classes /app/classes
COPY --from=build /app/target/dependency /app/lib

ENV DISPLAY=host.docker.internal:0.0

CMD ["java", "--module-path", "/app/lib", "--add-modules", "javafx.controls", "-cp", "/app/classes:/app/lib/*", "org.example.Main"]