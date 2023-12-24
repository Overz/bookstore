FROM openjdk:17-alpine

ENV WORKDIR='app'
ENV PORT='3000'
ENV APP='{{APP_NAME}}-{{APP_VERSION}}.jar'
ENV TZ='America/Sao_Paulo'

USER root

RUN unlink /etc/localtime && ln -s /usr/share/zoneinfo/${TZ} /etc/localtime

WORKDIR ${WORKDIR}

ADD ./build .

EXPOSE ${PORT}

