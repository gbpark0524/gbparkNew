#!/bin/bash
sudo bash -c 'source /home/ec2-user/bashrc && nohup java -jar -Dserver.port=80 /home/ec2-user/gbpark.jar > /home/ec2-user/app.log 2>&1 &'
