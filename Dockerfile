FROM openjdk:17-jdk-slim

COPY ./target/demo-0.0.1-SNAPSHOT-exec.jar app.jar
COPY ./entrypoint.sh .
COPY ./jmxremote.password /usr/local/openjdk-17/conf/management/jmxremote.password

RUN cp -p /usr/share/zoneinfo/Japan /etc/localtime
ENV JAVA_OPTS=-Duser.timezone=Asia/Tokyo

EXPOSE 8080/tcp
ENTRYPOINT ["bash", "entrypoint.sh"]
