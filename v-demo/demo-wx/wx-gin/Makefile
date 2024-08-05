
GO_BUILD_FLAGS := CGO_ENABLED=0
GOOS := $(shell go env GOOS)
GOARCH := $(shell go env GOARCH)
OUTPUT_NAME := app$(shell if [ "$(GOOS)" = "windows" ]; then echo ".exe"; fi)

go-build:
	go mod tidy
	$(GO_BUILD_FLAGS) GOOS=$(GOOS) GOARCH=$(GOARCH) go build -o $(OUTPUT_NAME) main.go

docker-build: clean
	CGO_ENABLED=0 GOOS=linux go build -o app main.go
	docker build -t demo-wx:latest .

clean:
	rm -f app app.exe