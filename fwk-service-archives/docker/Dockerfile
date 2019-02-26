FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-archives.jar /fwk-service-archives.jar
ENTRYPOINT ["java","-jar", "/fwk-service-archives.jar"]
CMD ["-D", "user.timezone=GMT+8"]