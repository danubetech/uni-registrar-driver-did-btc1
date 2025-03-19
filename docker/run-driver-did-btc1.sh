#!/usr/bin/env sh

chown -R jetty /opt/wallets

runuser -u jetty -- java -Djetty.http.port=9080 -jar /usr/local/jetty/start.jar