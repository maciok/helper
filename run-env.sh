#!/usr/bin/env bash
docker-compose  -f docker-compose.local-environment.yml up --build -V --abort-on-container-exit
