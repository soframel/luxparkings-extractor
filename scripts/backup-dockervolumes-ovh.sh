#!/bin/bash
scp -P 2244 sophie@54.38.158.160:/home/sophie/Backups/volumes.tgz .
timestamp=`date +"%s"`
cp volumes.tgz /tmp/volumes-$timestamp.tgz
