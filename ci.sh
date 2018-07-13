#! /bin/bash

IMAGE=kelog/five9s

docker build -t ${IMAGE}:backend-latest . && docker push ${IMAGE}:backend-latest

cd frontend
docker build -t ${IMAGE}:frontend-latest . && docker push ${IMAGE}:frontend-latest
