#!/bin/bash
sudo pkill -f 'java -jar' || true
  while pgrep -f 'java -jar' > /dev/null; do
    echo "Waiting for Java process to terminate..."
    sleep 2
  done