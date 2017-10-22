#!/bin/bash
echo ""
echo ""
echo "Starting provision at $(date +"%Y-%m-%d %H:%M:%S")"
#postgrebd
/opt/scripts/wait-for-it.sh postgresbdc:5432 -s -t 60 -- ./init-user-db.sh

# finish!
echo "Enviroment started and provisioned, check above if there is some error"
echo ""