FROM 192.168.0.130:1443/library/gradle:8.4.0-jdk11-bullseye

RUN apt-get update  \
    && apt-get install ca-certificates curl gnupg lsb-release gcc make g++ -y  \
    && install -m 0755 -d /etc/apt/keyrings \
    && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg \
    && chmod a+r /etc/apt/keyrings/docker.gpg \
    && echo \
         "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
         "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
         tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && apt-get update \
    && apt install docker-ce=5:20.10.20~3-0~debian-bullseye docker-ce-cli=5:20.10.20~3-0~debian-bullseye containerd.io docker-buildx-plugin docker-compose-plugin -y \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir -p ~/.ssh \
    && ssh-keygen -t rsa -b 4096 -f /root/.ssh/id_rsa -N ''

COPY deploy/daemon.json /etc/docker/daemon.json

VOLUME ["/home/gradle/.gradle"]
WORKDIR /home/gradle
