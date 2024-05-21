
.PHONY: build
build:
	docker build -t v-sqlite3:latest .

.PHONY: run
run:
	docker run -v /Users/lkzc19/TestEnv/vitamin:/app/db v-sqlite3:latest

.PHONY: all
all: build run