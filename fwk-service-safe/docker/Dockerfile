FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-safe.jar /fwk-service-safe.jar
ENTRYPOINT ["java","-jar", "/fwk-service-safe.jar"]
CMD ["-D", "user.timezone=GMT+8"]