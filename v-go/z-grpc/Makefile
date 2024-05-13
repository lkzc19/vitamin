
.PHONY: i
i:
	go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
	go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

.PHONY: g
g:
	protoc --go_out=./z-proto --go_opt=paths=source_relative --go-grpc_out=./z-proto --go-grpc_opt=paths=source_relative ./z-proto/*.proto
	cd z-proto && rm -f go.mod go.sum
	cd z-proto && go mod init z-proto
	cd z-proto && go mod tidy