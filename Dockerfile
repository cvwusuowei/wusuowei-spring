FROM openjdk:8
VOLUME /tmp
COPY target/wusuowei-springboot-0.0.1.jar blog.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/blog.jar"]
