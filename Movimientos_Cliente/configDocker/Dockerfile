FROM openjdk:17-jdk-alpine

RUN apk --no-cache add curl

WORKDIR /tmp
COPY *.jar app.jar
COPY ./config/application.properties /tmp/config/application.properties
ENV JAVA_OPTS "-Xmx192m -Xms192m -Djava.security.egd=file:/dev/./urandom --spring.config.location=file:///tmp/config/application.properties -XX:+HeapDumpOnOutOfMemoryError "
ENV TZ America/Guayaquil
# NOT USE YET -XX:+UseG1GC
ENTRYPOINT [ "sh", "-c", "java -jar app.jar $JAVA_OPTS" ]
