FROM bellsoft/liberica-openjre-alpine:17.0.11-cds

RUN mkdir -p /app/auth

WORKDIR /app/auth

ENV SERVER_PORT=8082 LANG=C.UTF-8 LC_ALL=C.UTF-8 JAVA_OPTS=""

EXPOSE ${SERVER_PORT}

ADD target/strr-auth.jar app.jar

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -Dserver.port=${SERVER_PORT} \
           -XX:+HeapDumpOnOutOfMemoryError -XX:+UseZGC ${JAVA_OPTS} \
           -jar app.jar
