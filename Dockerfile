FROM openjdk:11.0.12
ADD target/sandbox.jar sandbox.jar
EXPOSE 80
COPY . .
ENTRYPOINT ["java", "-javaagent:/usr/local/stackify/stackify-java-apm/stackify-java-apm.jar", "-jar", "sandbox.jar"]
