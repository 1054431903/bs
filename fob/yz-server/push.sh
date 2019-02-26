#!/bin/bash
docker build -t rc.ami-tek.com.cn:9082/agrithings/yz-server:1.0.0 .
docker push rc.ami-tek.com.cn:9082/agrithings/yz-server
