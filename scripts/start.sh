#!/bin/bash
sudo -E GBPARK_DB_URL=$GBPARK_DB_URL GBPARK_DB_USER=$GBPARK_DB_USER GBPARK_DB_PASS=$GBPARK_DB_PASS GBPARK_DB_DRIVER=$GBPARK_DB_DRIVER nohup java -jar -Dserver.port=80 /home/ec2-user/gbpark.jar > /home/ec2-user/app.log 2>&1 &
