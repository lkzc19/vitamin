
.PHONY: i
i:
	go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
	go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

.PHONY: proto-go
proto-go:
	protoc --go_out=./api/golang --go_opt=paths=source_relative --go-grpc_out=./api/golang --go-grpc_opt=paths=source_relative ./${SERVICE_NAME}/*.proto
	cd api/golang/${SERVICE_NAME}/ && rm go.mod && rm go.sum
	cd api/golang/${SERVICE_NAME}/ && go mod init github.com/ckmro/microservices-proto/golang/${SERVICE_NAME}
	cd api/golang/${SERVICE_NAME}/ && go mod tidy