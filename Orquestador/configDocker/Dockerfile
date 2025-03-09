FROM openjdk:17-jdk-alpine

# Instalar curl para posibles necesidades de pruebas o peticiones HTTP
RUN apk --no-cache add curl

# Establecer el directorio de trabajo
WORKDIR /tmp

# Copiar el archivo JAR y la configuración
COPY *.jar app.jar
COPY ./config/application.properties /tmp/config/application.properties

# Establecer las opciones de JVM
ENV JAVA_OPTS "-Xmx192m -Xms192m -Djava.security.egd=file:/dev/./urandom --spring.config.location=file:///tmp/config/application.properties -XX:+HeapDumpOnOutOfMemoryError "

# Configurar la zona horaria
ENV TZ America/Guayaquil

# Configuración de entrada
ENTRYPOINT ["sh", "-c", "java -jar app.jar $JAVA_OPTS"]
