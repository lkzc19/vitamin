
.PHONY: mongo-up
mongo-up:
	docker-compose -f mongo.compose.yml up -d

.PHONY: mongo-down
mongo-down:
	docker-compose -f mongo.compose.yml down

.PHONY: pgsql
pgsql:
	docker build -t demo-pgsql -f pgsql.Dockerfile .
	docker run --name demo-pgsql -p 3432:5432 -d demo-pgsql