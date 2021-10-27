#!/usr/bin/env bash

java -Dspring.datasource.url='jdbc:mysql://192.168.3.174:3306/repeater?serverTimezone=Asia/Chongqing&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true' \
     -Dspring.datasource.username=root -Dspring.datasource.password=123456 -jar target/repeater/repeater-bootstrap.jar