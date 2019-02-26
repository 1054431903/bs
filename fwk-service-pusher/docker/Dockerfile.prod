FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-pusher.jar /fwk-service-pusher.jar
ENTRYPOINT ["java","-jar", "/fwk-service-pusher.jar"]
CMD ["-D", "user.timezone=GMT+8"]