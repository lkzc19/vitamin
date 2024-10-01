# Custom
export ZBIN_HOME="$HOME/zbin"
export PATH=$PATH:$ZBIN_HOME

# JAVA
JAVA_HOME_8="/Library/Java/JavaVirtualMachines/jdk1.8.0_341.jdk/Contents/Home"
JAVA_HOME_11="/Library/Java/JavaVirtualMachines/jdk-11.0.14.jdk/Contents/Home"

JAVA_HOME=$JAVA_HOME_8
PATH=$PATH:$JAVA_HOME/bin
export JAVA_HOME
export PATH
# 用于切换JDK版本
alias jdk8="export JAVA_HOME=$JAVA_HOME_8"
alias jdk11="export JAVA_HOME=$JAVA_HOME_11"

# MAVEN
export M2_HOME="$HOME/Applications/apache-maven-3.6.3"
export PATH=$PATH:$M2_HOME/bin

#GRADLE
export GRADLE_HOME="$HOME/Applications/gradle-7.5.1"
export PATH=$PATH:$GRADLE_HOME/bin

# MYSQL
export MYSQL_HOME="/opt/homebrew/opt/mysql@5.7"
export PATH=$PATH:$MYSQL_HOME/bin
export LDFLAGS="-L/opt/homebrew/opt/mysql@5.7/lib"
export CPPFLAGS="-I/opt/homebrew/opt/mysql@5.7/include"

# MongoDB
export MONGODB_HOME=$HOME/Applications/mongodb-5.0.19
export PATH=$PATH:$MONGODB_HOME/bin

# Protoc
export PROTOC_HOME=$HOME/Applications/protoc-23.4
export PATH=$PATH:$PROTOC_HOME/bin

# nvm
export NVM_HOME="$HOME/Applications/nvm-0.39.5"
[ -s "$NVM_HOME/nvm.sh" ] && \. "$NVM_HOME/nvm.sh"
[ -s "$NVM_HOME/bash_completion" ] && \. "$NVM_HOME/bash_completion"
export NVM_NODEJS_ORG_MIRROR=https://npm.taobao.org/mirrors/node/


