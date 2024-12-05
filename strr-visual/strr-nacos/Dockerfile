FROM bellsoft/liberica-openjre-alpine:17.0.11-cds

RUN mkdir -p /app/nacos

WORKDIR /app/nacos

ENV SERVER_PORT=8848 TZ=Asia/Shanghai LANG=C.UTF-8 LC_ALL=C.UTF-8 JAVA_OPTS="-Xms512m -Xmx1024m"

EXPOSE ${SERVER_PORT}

ADD target/strr-nacos.jar app.jar

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -Dserver.port=${SERVER_PORT} \
           -XX:+HeapDumpOnOutOfMemoryError -XX:+UseZGC ${JAVA_OPTS} \
           -jar app.jar
