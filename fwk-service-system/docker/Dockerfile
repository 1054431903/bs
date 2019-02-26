FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-system.jar /fwk-service-system.jar
ENTRYPOINT ["java","-jar", "/fwk-service-system.jar"]
CMD ["-D", "user.timezone=GMT+8"]