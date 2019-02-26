FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-user.jar /fwk-service-user.jar
ENTRYPOINT ["java","-jar", "/fwk-service-user.jar"]
CMD ["-D", "user.timezone=GMT+8"]