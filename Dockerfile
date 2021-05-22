FROM maven:3.6.1-jdk-13 AS build

EXPOSE 8080

ENV BASE_DIR /spring-boot-base-learning
ENV DEFAULT_JAR_NAME base-learning-1.0.0.jar
ENV JAR_PATH target/${DEFAULT_JAR_NAME}

WORKDIR ${BASE_DIR}

COPY src ./src
COPY pom.xml ./
RUN mvn clean package -Dmaven.test.skip && rm -r target/

COPY ./ ${BASE_DIR}

RUN mvn clean package

FROM openjdk:10.0.2-13-jre
ARG DEFAULT_PROFILE=staging
ENV BASE_DIR /spring-boot-base-learning
ENV DEFAULT_JAR_NAME base-learning-1.0.0.jar
ENV JAR_PATH target/${DEFAULT_JAR_NAME}
WORKDIR ${BASE_DIR}
COPY --from=0 ${BASE_DIR}/${JAR_PATH} ${BASE_DIR}
ENTRYPOINT exec java -XX:+UnlockExperimentalVMOptions -cp ${BASE_DIR}/${DEFAULT_JAR_NAME} com.softkit.StarterApplication
