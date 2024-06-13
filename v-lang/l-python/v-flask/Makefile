

.PHONY: build
build:
	pip install pipreqs
	pipreqs ./ --encoding=utf8 --force
	docker build -t v-flask:latest .

.PHONY: run
run:
	docker run -d --name v-flask -p 4000:4000 v-flask:latest

.PHONY: all
all: build run