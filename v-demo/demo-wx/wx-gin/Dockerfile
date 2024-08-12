FROM golang:latest AS builder
LABEL authors="lkzc19"

ENV TZ=Asia/Shanghai

WORKDIR /build
COPY . .
RUN go mod tidy && \
    go build -o app main.go

FROM alpine

ENV TZ=Asia/Shanghai

WORKDIR /app
COPY --from=builder /build/app /app/app

EXPOSE 3000

ENTRYPOINT ["./app"]
