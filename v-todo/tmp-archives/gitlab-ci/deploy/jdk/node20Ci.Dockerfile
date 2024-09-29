FROM node:20-bullseye

ENV JAVA_HOME /opt/java/openjdk
ENV PATH $JAVA_HOME/bin:$PATH

# Default to UTF-8 file.encoding
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'
RUN sed -i 's/deb.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list \
    && sed -i 's|security.debian.org/debian-security|mirrors.ustc.edu.cn/debian-security|g' /etc/apt/sources.list
RUN set -eux; \
    apt-get update; \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        # curl required for historical reasons, see https://github.com/adoptium/containers/issues/255
        curl \
        wget \
        fontconfig \
        # utilities for keeping Ubuntu and OpenJDK CA certificates in sync
        # https://github.com/adoptium/containers/issues/293
        ca-certificates p11-kit \
        tzdata \
        # locales ensures proper character encoding and locale-specific behaviors using en_US.UTF-8
        locales \
    ; \
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen; \
    locale-gen en_US.UTF-8; \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_VERSION jdk-11.0.21+9

RUN set -eux; \
    ARCH="$(dpkg --print-architecture)"; \
    case "${ARCH}" in \
       aarch64|arm64) \
         BINARY_URL='https://mirrors-i.tuna.tsinghua.edu.cn/Adoptium/11/jdk/aarch64/linux/OpenJDK11U-jdk_aarch64_linux_hotspot_11.0.23_9.tar.gz'; \
         ;; \
       amd64|i386:x86-64) \
         BINARY_URL='https://mirrors-i.tuna.tsinghua.edu.cn/Adoptium/11/jdk/x64/linux/OpenJDK11U-jdk_x64_linux_hotspot_11.0.23_9.tar.gz'; \
         ;; \
       armhf|arm) \
         BINARY_URL='https://mirrors-i.tuna.tsinghua.edu.cn/Adoptium/11/jdk/arm/linux/OpenJDK11U-jdk_arm_linux_hotspot_11.0.23_9.tar.gz'; \
         ;; \
       ppc64el|powerpc:common64) \
         BINARY_URL='https://mirrors-i.tuna.tsinghua.edu.cn/Adoptium/11/jdk/ppc64le/linux/OpenJDK11U-jdk_ppc64le_linux_hotspot_11.0.23_9.tar.gz'; \
         ;; \
       s390x|s390:64-bit) \
         BINARY_URL='https://mirrors-i.tuna.tsinghua.edu.cn/Adoptium/11/jdk/s390x/linux/OpenJDK11U-jdk_s390x_linux_hotspot_11.0.23_9.tar.gz'; \
         ;; \
       *) \
         echo "Unsupported arch: ${ARCH}"; \
         exit 1; \
         ;; \
    esac; \
    wget --progress=dot:giga -O /tmp/openjdk.tar.gz ${BINARY_URL}; \
    mkdir -p "$JAVA_HOME"; \
    tar --extract \
        --file /tmp/openjdk.tar.gz \
        --directory "$JAVA_HOME" \
        --strip-components 1 \
        --no-same-owner \
    ; \
    rm -f /tmp/openjdk.tar.gz ${JAVA_HOME}/lib/src.zip; \
    # https://github.com/docker-library/openjdk/issues/331#issuecomment-498834472
    find "$JAVA_HOME/lib" -name '*.so' -exec dirname '{}' ';' | sort -u > /etc/ld.so.conf.d/docker-openjdk.conf; \
    ldconfig; \
    # https://github.com/docker-library/openjdk/issues/212#issuecomment-420979840
    # https://openjdk.java.net/jeps/341
    java -Xshare:dump;

RUN set -eux; \
    echo "Verifying install ..."; \
    fileEncoding="$(echo 'System.out.println(System.getProperty("file.encoding"))' | jshell -s -)"; [ "$fileEncoding" = 'UTF-8' ]; rm -rf ~/.java; \
    echo "javac --version"; javac --version; \
    echo "java --version"; java --version; \
    echo "Complete."
COPY deploy/jdk/entrypoint.sh /__cacert_entrypoint.sh
ENTRYPOINT ["/__cacert_entrypoint.sh"]

CMD ["jshell"]

RUN apt-get update  \
    && apt-get install ca-certificates curl gnupg lsb-release gcc make g++ -y  \
    && rm -rf /var/lib/apt/lists/*

VOLUME ["/home/gradle/.gradle"]
WORKDIR /home/gradle
