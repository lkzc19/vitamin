FROM node:18-bullseye

# Default to UTF-8 file.encoding
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'
RUN sed -i 's/deb.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list \
    && sed -i 's|security.debian.org/debian-security|mirrors.ustc.edu.cn/debian-security|g' /etc/apt/sources.list && \
    apt-get update && apt-get upgrade -y && \
    apt-get install curl ca-certificates gnupg lsb-release gcc make  g++  maven  ant  ruby-full wget fontconfig ca-certificates p11-kit tzdata locales openjdk-11-jdk openjdk-17-jdk -y && \
    rm -rf /var/lib/apt/lists/* && \
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen en_US.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV JDK_HOME=${JAVA_HOME}
ENV JRE_HOME=${JDK_HOME}
ENV MAVEN_VERSION=3.9.5
ENV GRADLE_USER_HOME /home/gradle/.gradle


VOLUME ["/home/gradle/.gradle"]
CMD ["jshell"]
# Install Android SDK Tools
RUN mkdir -p /home/android/android-sdk
ENV ANDROID_HOME "/home/android/android-sdk"
ENV ANDROID_SDK_ROOT $ANDROID_HOME
ENV CMDLINE_TOOLS_ROOT "${ANDROID_HOME}/cmdline-tools/latest/bin"
ENV ADB_INSTALL_TIMEOUT 120
ENV PATH "${ANDROID_HOME}/emulator:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/platform-tools/bin:${PATH}"
# You can find the latest command line tools here: https://developer.android.com/studio#command-line-tools-only
RUN SDK_TOOLS_URL="https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip" && \
	mkdir -p ${ANDROID_HOME}/cmdline-tools && \
	mkdir ${ANDROID_HOME}/platforms && \
	mkdir ${ANDROID_HOME}/ndk && \
	wget -O /tmp/cmdline-tools.zip -t 5 "${SDK_TOOLS_URL}" && \
	unzip -q /tmp/cmdline-tools.zip -d ${ANDROID_HOME}/cmdline-tools && \
	rm /tmp/cmdline-tools.zip && \
	mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest

RUN echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "tools" && \
	echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platform-tools" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "build-tools;34.0.0-rc3" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "build-tools;33.0.2" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "build-tools;32.1.0-rc1"

RUN echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-28" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-29" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-30" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-31" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-32" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-33" && \
    echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "platforms;android-34"

# Install some useful packages
RUN echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "extras;android;m2repository" && \
	echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "extras;google;m2repository" && \
	echo y | ${CMDLINE_TOOLS_ROOT}/sdkmanager "extras;google;google_play_services"
