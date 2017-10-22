FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp


ARG JAR_VERSION
ADD api-weather-service-${JAR_VERSION}.jar api-weather-service.jar

RUN sh -c 'touch /api-weather-service.jar'

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /api-weather-service.jar" ]
