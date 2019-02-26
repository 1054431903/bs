#!/bin/bash
docker build -t agrithings/fob-server:1.0.0 -f ../frontend/Dockerfile.prod ../frontend
#docker push rc.ami-tek.com.cn:9082/agrithings/fob-server
