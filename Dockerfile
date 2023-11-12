FROM openjdk:11-jre-slim
ADD target/kalvad-test-docker.jar kalvad-test-docker.jar
ENTRYPOINT ["java","-jar","/kalvad-test-docker.jar"]