#!/bin/bash

# Build and start Docker containers
docker-compose up -d

# Wait for service-registry to be ready
dockerize -wait tcp://service-registry:8761 -timeout 60s

