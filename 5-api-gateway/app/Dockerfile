#java 11
FROM adoptopenjdk/openjdk11:alpine-jre

MAINTAINER Synergyteam <ivakhin@synergyteam.msk.ru>

# Set timezone ENV and install tzdata
ENV TZ=UTC

RUN apk add --no-cache tzdata
# Needed to fix 'Fontconfig warning: ignoring C.UTF-8: not a valid language tag'
ENV LANG en_GB.UTF-8

ARG REVISION=develop
ARG CI_PIPELINE_IID=NA
COPY ./build/libs/ws-user.jar ws-user.jar

#запуск
ARG SPRING_SERVER_PORT=8000
ARG SPRING_MANAGEMENT_PORT=9990
ARG SPRING_PROFILE=docker
ARG SPRING_DATASOURCE_URL=jdbc:postgresql://pg0:5432/db_user?currentSchema=public
ARG SPRING_DATASOURCE_USER=acdb
ARG SPRING_DATASOURCE_PWD=acdb
ARG SWAGGER_ENABLE=true

ENV SPRING_SERVER_PORT=$SPRING_SERVER_PORT
ENV SPRING_MANAGEMENT_PORT=$SPRING_MANAGEMENT_PORT
ENV SPRING_PROFILE=$SPRING_PROFILE
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_USER=$SPRING_DATASOURCE_USER
ENV SPRING_DATASOURCE_PWD=$SPRING_DATASOURCE_PWD
ENV SWAGGER_ENABLE=$SWAGGER_ENABLE

#порт
EXPOSE $SPRING_SERVER_PORT $SPRING_MANAGEMENT_PORT

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILE}", \
    "ws-user.jar", \
    "--asuer.application.swaggerEnabled=${SWAGGER_ENABLE}", \
    "--server.port=${SPRING_SERVER_PORT}", \
    "--management.server.port=${SPRING_MANAGEMENT_PORT}", \
    "--spring.datasource.url=${SPRING_DATASOURCE_URL}", \
    "--spring.datasource.username=${SPRING_DATASOURCE_USER}", \
    "--spring.datasource.password=${SPRING_DATASOURCE_PWD}" ]