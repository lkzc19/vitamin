

.PHONY: build
build:
	pip install pipreqs
	pipreqs ./ --encoding=utf8 --force
	docker build -t v-flask:latest .

.PHONY: run
run:
	docker run -d --name v-flask -p 3000:3000 v-flask:latest

.PHONY: all
all: build run