#!/bin/bash
set -e
rm -rf *.zip
./gradlew clean test integrationTest assemble
EXIT_STATUS=0
exit $EXIT_STATUS