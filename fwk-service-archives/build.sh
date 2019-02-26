# /bin/bash
mvn clean deploy -Dmaven.test.skip=true
cp target/fwk-service-archives-3.0.0-SNAPSHOT.jar docker/fwk-service-archives.jar