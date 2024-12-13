FROM eclipse-temurin:23-noble AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN chmod a+x ./mvnw && ./mvnw clean package -Dmaven.test.skip=true


FROM eclipse-temurin:23-jre-noble

WORKDIR /app

COPY --from=builder /src/target/noticeboard-0.0.1-SNAPSHOT.jar app.jar

RUN apt update && apt install -y curl

ENV PORT=8080
ENV NOTICEBOARD_DB_HOST=
ENV NOTICEBOARD_DB_PORT=
ENV NOTICEBOARD_DB_DATABASE=
ENV NOTICEBOARD_DB_USERNAME=
ENV NOTICEBOARD_DB_PASSWORD=
ENV PUBLISHING_HOST=

EXPOSE ${PORT}

HEALTHCHECK --interval=60s --timeout=30s --start-period=120s --retries=3 \
    CMD curl -s -f http://localhost:${PORT}/status || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar