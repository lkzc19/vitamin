FROM node:lts-bullseye-slim

LABEL describe="gitlab跑CI时要用到的node+docker底包"

RUN sed -i 's/deb.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list \
    && sed -i 's|security.debian.org/debian-security|mirrors.ustc.edu.cn/debian-security|g' /etc/apt/sources.list \
    && apt-get update \
    && apt-get upgrade -y \
    && apt-get install ca-certificates curl gnupg lsb-release gcc make g++ wget expect -y \
    && install -m 0755 -d /etc/apt/keyrings \
    && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg \
    && chmod a+r /etc/apt/keyrings/docker.gpg \
    && echo \
        "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
        "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
        tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && apt-get update \
    && apt install docker-ce=5:20.10.20~3-0~debian-bullseye docker-ce-cli=5:20.10.20~3-0~debian-bullseye containerd.io docker-buildx-plugin docker-compose-plugin -y \
    && rm -rf /var/lib/apt/lists/* 