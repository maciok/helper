FROM openjdk:12-jdk-oraclelinux7

CMD pwd
CMD ls
COPY target/helper-0.0.1-SNAPSHOT.jar /helper.jar
EXPOSE 8080

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/helper.jar" ]
