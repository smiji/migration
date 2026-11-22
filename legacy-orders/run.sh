#!/usr/bin/env bash
# TRAP: legacy JVM flags. JVM will refuse to start on Java 14+ because of CMS.
java -XX:+UseConcMarkSweepGC \
     -XX:MaxPermSize=256m \
     -XX:+PrintGCDetails \
     -Xmx512m \
     -jar target/legacy-orders-1.0.0.jar
