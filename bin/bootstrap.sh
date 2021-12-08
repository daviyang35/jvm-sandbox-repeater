#!/usr/bin/env bash

# exit shell with err_code
# $1 : err_code
# $2 : err_msg
exit_on_err()
{
    [[ ! -z "${2}" ]] && echo "${2}" 1>&2
    exit ${1}
}

PID=$(ps -ef | grep "repeater-console-bootstrap.jar" | grep "java" | grep -v grep | awk '{print $2}')

expr ${PID} "+" 10 &> /dev/null

# if occurred error,exit
if [ ! $? -eq 0 ] || [ "" = "${PID}" ] ;then
    echo ""
else
    echo "found target pid exist, pid is ${PID}, kill it..."
    kill -9 ${PID}
fi

#if [ ! -f "${HOME}/.sandbox-module/repeater-bootstrap.jar" ]; then
#    echo "repeater-bootstrap.jar not found, try to install";
#    sh ./install-local.sh || exit_on_err 1 "install repeater failed"
#fi

java -javaagent:${HOME}/sandbox/lib/sandbox-agent.jar=server.port=8820\;server.ip=0.0.0.0 -DREPATER_APP_NAME=repeater -DREPATER_APP_ENV=daily \
     -jar ../dist/repeater-bootstrap.jar
