#!/bin/bash
set -a
source /home/ec2-user/.env
set +a
sudo -E nohup java -jar /home/ec2-user/gbpark.jar > /home/ec2-user/app.log 2>&1 &
