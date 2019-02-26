FROM anapsix/alpine-java:8
RUN echo "Asia/Shanghai" > /etc/timezone
COPY fwk-service-schedule.jar /fwk-service-schedule.jar
ENTRYPOINT ["java","-jar", "/fwk-service-schedule.jar"]
CMD ["-D", "user.timezone=GMT+8"]