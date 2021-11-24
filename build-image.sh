#!/usr/bin/env bash

# exit shell with err_code
# $1 : err_code
# $2 : err_msg
exit_on_err()
{
    [[ ! -z "${2}" ]] && echo "${2}" 1>&2
    exit ${1}
}

# build
mvn clean package -Dmaven.test.skip=true || exit_on_err 1 "package repeater failed."

# copy files
sh bin/package.sh || exit_on_err 1 "copy repeater failed."

docker build -f Dockerfile.console -t jvm-sandbox-console . || exit_on_err 1 "package repeater failed."
docker build -f Dockerfile.agent -t openjdk:8-jdk-repeater . || exit_on_err 1 "package repeater failed."
