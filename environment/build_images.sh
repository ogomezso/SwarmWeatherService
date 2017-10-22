#!/usr/bin/env bash
echo "**********************************************"
echo "Environment Images: Building!"
echo "Profile: " $PROFILES_ACTIVE
echo "**********************************************"

## Default value PROFILES_ACTIVE = dev
if [ -z "$PROFILES_ACTIVE" ]
then
  export PROFILES_ACTIVE="dev"
fi


CURRENT_DIR=$PWD

if [[ "$PROFILES_ACTIVE" == "dev" ]]
then
    #base
    docker build -t base containers/base/
    #provision
    docker build -t provision containers/provision/
fi

#api-weather-service
cd ../api-weather-service      && mvn clean package docker:build && cd $CURRENT_DIR
#weather-service
cd ../weather-service          && mvn clean package docker:build && cd $CURRENT_DIR
#daas-service
cd ../daas-weather-service     && mvn clean package docker:build && cd $CURRENT_DIR


echo "**********************************************"
echo "Environment Images are build succesfully!"
echo "Profile: "  $PROFILES_ACTIVE
echo "**********************************************"
exit 0
