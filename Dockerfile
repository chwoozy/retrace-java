FROM openjdk:11
ADD target/sandbox.jar sandbox.jar
EXPOSE 80
ENTRYPOINT ["java", "-javaagent:/usr/local/stackify/stackify-java-apm/stackify-java-apm.jar", "-jar", "sandbox.jar"]