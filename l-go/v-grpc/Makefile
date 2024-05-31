
.PHONY: i
i:
	go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
	go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

.PHONY: g
g:
	protoc --go_out=. --go_opt=paths=source_relative --go-grpc_out=. --go-grpc_opt=paths=source_relative ./v-proto/*.proto
	cd v-proto && rm -f go.mod go.sum
	cd v-proto && go mod init v-proto
	cd v-proto && go mod tidy