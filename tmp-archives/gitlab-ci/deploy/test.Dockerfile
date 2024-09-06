FROM 192.168.0.130:1443/library/amazoncorretto:11-alpine

ARG BUILDVER=v1.0.0
ARG APPLICATION_VERSION
ARG KTOR_VERSION

ENV TIME_ZONE="Asia/Shanghai"
#    JAVA_OPTS="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC"
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories  \
    && echo "https://mirrors.aliyun.com/alpine/edge/testing/" >> /etc/apk/repositories \
    && echo "https://mirrors.aliyun.com/alpine/edge/community/" >> /etc/apk/repositories \
    && echo "https://mirrors.aliyun.com/alpine/edge/main" >> /etc/apk/repositories \
    && apk add --update font-wqy-zenhei ttf-dejavu fontconfig  \
    && rm -rf /var/cache/apk/*  \
    && mkfontscale  \
    && mkfontdir  \
    && fc-cache \
    && mkdir /app

WORKDIR /app
#TODO 下面这个字体是不可商用字体，上线前要想办法替换不然要吃官司的
COPY font/STSONG-light.ttf /usr/share/fonts/win/STSONG-light.ttf
COPY gia-app/build/libs/giakt.$BUILDVER-all.jar app.jar
COPY .env.test .env

ENV BUILDVER=$BUILDVER ENV=test APPLICATION_VERSION=$APPLICATION_VERSION KTOR_VERSION=$KTOR_VERSION

ENTRYPOINT ["java", "-jar", "app.jar", "$JAVA_OPTS"]

EXPOSE 6789
