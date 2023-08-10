# syntax=docker/dockerfile:1
ARG BASE_IMAGE_ACR=gacr2pbaseimages
ARG ACR_REPO="gradle"
ARG BASE_IMAGE_VERSION="7-jdk11"
# ARG BASE_IMAGE_VERSION="7-jdk17" # uncomment and remove the line above if you want to use j17
FROM ${BASE_IMAGE_ACR}.azurecr.io/${ACR_REPO}:${BASE_IMAGE_VERSION} AS build

ARG IMAGE_DATE
ARG ARTIFACTORY_USERNAME
ARG ARTIFACTORY_PASSWORD
ARG APPLICATION_VERSION
ARG JASYPT_ENCRYPTOR_PASSWORD
ARG SONAR_TOKEN

ENV ARTIFACTORY_USERNAME=${ARTIFACTORY_USERNAME}
ENV ARTIFACTORY_PASSWORD=${ARTIFACTORY_PASSWORD}
ENV JASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD}
ENV SAAS_ARTIFACTORY_USERNAME=${ARTIFACTORY_USERNAME}
ENV SAAS_ARTIFACTORY_PASSWORD=${ARTIFACTORY_PASSWORD}
ENV SONAR_TOKEN=${SONAR_TOKEN}

WORKDIR /source

COPY --chown=gradle:gradle . .

RUN chmod +x gradlew && ./gradlew -Dgradle.wrapperUser=${ARTIFACTORY_USERNAME} -Dgradle.wrapperPassword=${ARTIFACTORY_PASSWORD} clean build sonar -Dsonar.analysis.mode=publish -Pversion=${APPLICATION_VERSION} -PbuildDate=${IMAGE_DATE}

FROM gacr2pbaseimages.azurecr.io/gap/baseimages/openjdk11-alpine:0.0.8 AS app
# FROM gacr2pbaseimages.azurecr.io/gap/baseimages/openjdk17-alpine:0.0.5 AS app # uncomment and remove the above line if you want ot use j17

ARG SERVICE_NAME
ARG DEPENDENCY=build/libs
ARG PORT=8080
ARG APPLICATION_VERSION
ARG IMAGE_DATE
ARG IMAGE_MAINTAINER=""
ARG PROFILE

ENV APPLICATION_VERSION ${APPLICATION_VERSION}
ENV PROFILE ${PROFILE}

WORKDIR /app/opt

USER www-data

COPY --from=build /source/${DEPENDENCY}/${SERVICE_NAME}-${APPLICATION_VERSION}.jar .
ENV SERVICE_PATH="/app/opt/${SERVICE_NAME}-${APPLICATION_VERSION}.jar"

LABEL maintainer=${IMAGE_MAINTAINER}
LABEL com.gap.${SERVICE_NAME}.version=${APPLICATION_VERSION}
LABEL com.gap.${SERVICE_NAME}.product=${SERVICE_NAME}
LABEL com.gap.${SERVICE_NAME}.release-date=${IMAGE_DATE}

EXPOSE ${PORT}
ENTRYPOINT ["sh","-c","java $JAVA_NR_OPTS $JAVA_OPTS -jar -Dspring.profiles.active=${PROFILE} ${SERVICE_PATH} "]

#WORKDIR /var/www - Uncomment for Dev testing
#WORKDIR /app/opt/tempFile - - Uncomment for Dev testing