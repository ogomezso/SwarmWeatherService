#!/usr/bin/env bash

####################################
#          DANGER ZONE             #
# DONT MODIFY ANYTHING BEYOND THIS #
# LINE IF YOU ARE NOT SURE         #
####################################
if [ "$#" -lt 1 ]
then
  echo "Usage: $0 start/stop/reset/clean-all" >&2
  exit 1
fi

MY_PATH="`dirname \"$0\"`" # relative
MY_PATH="`( cd \"$MY_PATH\" && pwd )`"

ENV_PHASE1="`( cd \"$MY_PATH\" && cd ../environment && pwd )`"

COMMAND=$1
if [ "$COMMAND" == "start" ]
then
  echo 'Starting Environment...'
  docker-compose -f docker-compose-env.yml up -d --remove-orphans
  docker logs -f provision
elif [ "$COMMAND" == "stop" ]
then
  docker-compose -f docker-compose-env.yml stop

elif [ "$COMMAND" == "reset" ]
then
  docker-compose -f docker-compose-env.yml rm -f --remove-orphans
elif [ "$COMMAND" == "clean-all" ]
then
  docker-compose -f docker-compose-env.yml kill
else
  echo "Unknown option ${COMMAND}, usage: $0 start/stop/reset/clean-all" >&2
  exit 1
fi
