FROM bellsoft/liberica-openjre-debian:21

WORKDIR /opt

EXPOSE 8080

COPY target/*.jar /opt/app.jar

ENTRYPOINT ["/bin/sh", "-c", "java -jar /opt/app.jar"]