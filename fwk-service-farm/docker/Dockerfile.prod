FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-farm.jar /fwk-service-farm.jar
ENTRYPOINT ["java","-jar", "/fwk-service-farm.jar"]
CMD ["-D", "user.timezone=GMT+8"]