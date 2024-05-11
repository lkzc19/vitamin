
.PHONY: pgsql
pgsql:
	docker build -t demo-pgsql -f pgsql.Dockerfile .
	docker run --name demo-pgsql -p 3432:5432 -d demo-pgsql