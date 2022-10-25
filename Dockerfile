# syntax=docker/dockerfile:experimental
FROM gradle:7.5.1-jdk11-alpine AS build-env

# set the working directory to app
WORKDIR /app

# Copy local code to container img
COPY . .

# RUN gradle shadowJar
RUN gradle clean shadowJar

# Use AdoptOpenJDK for base image
FROM adoptopenjdk/openjdk11:alpine-slim

COPY --from=build-env /app/build/libs/nr-phrase-sorter-all.jar /nr-phrase-sorter.jar
COPY --from=build-env /app/scripts/startup.sh /startup.sh

ENTRYPOINT ["sh", "/startup.sh"]