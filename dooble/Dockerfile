FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/dooble-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY lanzador.sh /
COPY objetos.csv /
CMD sh /lanzador.sh
