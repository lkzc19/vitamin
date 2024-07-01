.PHONY: all build run

build:
	docker build -t collector-nginx:latest .

stop:
	docker stop collector-nginx || true
	docker rm collector-nginx || true

run: stop
	docker run --name collector-nginx -p 3333:80 -d collector-nginx

all: build run