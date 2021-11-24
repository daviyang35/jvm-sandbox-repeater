#!/usr/bin/env bash

# repeater's target dir
BUILD_OUTPUT_DIR=../dist
REPEATER_TARGET_DIR=${BUILD_OUTPUT_DIR}/sandbox-module

# exit shell with err_code
# $1 : err_code
# $2 : err_msg
exit_on_err()
{
    [[ ! -z "${2}" ]] && echo "${2}" 1>&2
    exit ${1}
}

rm -rf ${BUILD_OUTPUT_DIR}

mkdir -p ${REPEATER_TARGET_DIR}/plugins
mkdir -p ${REPEATER_TARGET_DIR}/cfg

# Repeater Backend Service
cp ../repeater-console/repeater-console-bootstrap/target/repeater-console-bootstrap.jar ${BUILD_OUTPUT_DIR}/repeater-console-bootstrap.jar

# Repeater Module Config
cp ./cfg/repeater-logback.xml ${REPEATER_TARGET_DIR}/cfg/repeater-logback.xml
cp ./cfg/repeater.properties ${REPEATER_TARGET_DIR}/cfg/repeater.properties

# Repeater Module Manager
cp ../repeater-agent/repeater-module/target/repeater-module-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/repeater-module.jar

# Repeater Module Plugins
cp ../repeater-agent/repeater-plugins/redis-plugin/target/redis-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/redis-plugin.jar
cp ../repeater-agent/repeater-plugins/apache-http-client-plugin/target/apache-http-client-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/apache-http-client-plugin.jar
cp ../repeater-agent/repeater-plugins/http-plugin/target/http-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/http-plugin.jar
cp ../repeater-agent/repeater-plugins/java-plugin/target/java-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/java-plugin.jar
cp ../repeater-agent/repeater-plugins/eh-cache-plugin/target/eh-cache-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/eh-cache-plugin.jar
cp ../repeater-agent/repeater-plugins/ibatis-plugin/target/ibatis-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/ibatis-plugin.jar
cp ../repeater-agent/repeater-plugins/caffeine-cache-plugin/target/caffeine-cache-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/caffeine-cache-plugin.jar
cp ../repeater-agent/repeater-plugins/guava-cache-plugin/target/guava-cache-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/guava-cache-plugin.jar
cp ../repeater-agent/repeater-plugins/socketio-plugin/target/socketio-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/socketio-plugin.jar
cp ../repeater-agent/repeater-plugins/jpa-plugin/target/jpa-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/jpa-plugin.jar
cp ../repeater-agent/repeater-plugins/hibernate-plugin/target/hibernate-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/hibernate-plugin.jar
cp ../repeater-agent/repeater-plugins/mybatis-plugin/target/mybatis-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/mybatis-plugin.jar
cp ../repeater-agent/repeater-plugins/okhttp-plugin/target/okhttp-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/okhttp-plugin.jar
cp ../repeater-agent/repeater-plugins/dubbo-plugin/target/dubbo-plugin-*-jar-with-dependencies.jar ${REPEATER_TARGET_DIR}/plugins/dubbo-plugin.jar
