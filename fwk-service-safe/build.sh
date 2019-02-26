# /bin/bash
mvn clean deploy -Dmaven.test.skip=true
cp target/fwk-service-safe-3.0.0-SNAPSHOT.jar docker/fwk-service-safe.jar