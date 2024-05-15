
.PHONY: mongo-up
mongo-up:
	docker-compose --project-name v-mongo -f mongo.compose.yml up -d

.PHONY: mongo-down
mongo-down:
	docker-compose -f mongo.compose.yml down

.PHONY: pgsql-up
pgsql-up:
	docker-compose --project-name v-pgsql -f pgsql.compose.yml up -d

.PHONY: pgsql-down
pgsql-down:
	docker-compose -f pgsql.compose.yml down