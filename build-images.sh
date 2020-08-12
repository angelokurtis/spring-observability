#!/bin/bash

set -e
set -x

mvn package jib:dockerBuild -f ./client-app
mvn package jib:dockerBuild -f ./server-app
