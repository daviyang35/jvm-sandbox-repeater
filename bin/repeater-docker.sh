#!/usr/bin/env sh
# 允许错误发生
set +e

# 设置镜像
sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

# 安装依赖 glib
apk add --no-cache --update libc6-compat

# 检查环境
# wget
which wget > /dev/null || apk add --no-cache --update wget
# curl
which curl > /dev/null || apk add --no-cache --update curl

# 安装 jattach
jattach > /dev/null || wget http://172.10.0.26:9000/common/jattach -O /usr/local/bin/jattach && chmod +x /usr/local/bin/jattach

# 安装 tools.jar
ls $JAVA_HOME/lib/tools.jar > /dev/null || wget http://172.10.0.26:9000/common/tools.jar -O $JAVA_HOME/lib/tools.jar

# 安装 sandbox sandbox-repeater
wget http://172.10.0.26:9000/common/sandbox.tar.gz -O /tmp/sandbox.tar.gz && tar zxvf /tmp/sandbox.tar.gz -C /root
wget http://172.10.0.26:9000/common/sandbox-module.tar.gz -O /tmp/sandbox-module.tar.gz && \
  tar zxvf /tmp/sandbox-module.tar.gz -C /root && \
  mv /root/sandbox-module /root/.sandbox-module
rm -f /tmp/sandbox.tar.gz && rm -f /tmp/sandbox-module.tar.gz

# 设置 repeater-console 地址
sed -i 's/localhost:8001/aos-repeater-console:8001/g' /root/.sandbox-module/cfg/repeater.properties

# 强制指定 hosts aos-repeater-console 地址
echo "192.168.3.174 aos-repeater-console" >> /etc/hosts

# JVM 载入 sandbox
export token="$(date | head | cksum | sed 's/ //g')"
echo $token
pgrep java | xargs -I "{}" jattach {} load instrument false /root/sandbox/lib/sandbox-agent.jar="home=/root/sandbox;namespace=default;token=${token};server.ip=0.0.0.0;server.port=0"
