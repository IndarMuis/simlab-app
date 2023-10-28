FROM eclipse-temurin:17-jdk-ubi9-minimal AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline
COPY ./src ./src
RUN  ./mvnw clean package -Dmaven.test.skip

FROM eclipse-temurin:17.0.5_8-jre-ubi9-minimal AS layered
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} /app/app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.5_8-jre-ubi9-minimal
WORKDIR /app
COPY --from=layered /app/dependencies/ ./
COPY --from=layered /app/snapshot-dependencies/ ./
COPY --from=layered /app/spring-boot-loader/ ./
COPY --from=layered /app/application/ ./

ENV DATABASE_ADDR=localhost
ENV DATABASE_USER=user
ENV DATABASE_NAME=db_name
ENV DATABASE_PASSWORD=password

RUN useradd appuser
USER appuser

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]