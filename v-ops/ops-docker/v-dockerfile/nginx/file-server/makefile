DOCKER_NAME=nginx

.PHONY: all build run

build:
	docker build -t $(DOCKER_NAME):latest .

stop:
	docker stop $(DOCKER_NAME) || true
	docker rm $(DOCKER_NAME) || true

run: stop
	docker run --name $(DOCKER_NAME) -p 3000:80 -d $(DOCKER_NAME)

all: build run