FROM openjdk:8-jre-slim

RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    sed -i 's/security.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    apt-get update

WORKDIR /app

COPY dist/ /app/frontend

COPY bookmark-api.jar /app/backend.jar

COPY nginx.conf /etc/nginx/conf.d/default.conf

RUN apt-get install -y nginx && apt-get clean

EXPOSE 9523

CMD service nginx start && java -jar /app/backend.jar

