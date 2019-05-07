#!/bin/bash

gradle build
scp -i ~/.ssh/amazon ./build/libs/qaengine-0.0.1-SNAPSHOT.jar ubuntu@3.82.117.200:~/app.jar
ssh -f -i ~/.ssh/amazon ubuntu@3.82.117.200 "./start-app.sh"
