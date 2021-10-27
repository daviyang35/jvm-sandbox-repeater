#!/usr/bin/env bash

docker build -f Dockerfile.console -t jvm-sandbox-console .
docker build -f Dockerfile.agent -t jvm-sandbox-agent .
