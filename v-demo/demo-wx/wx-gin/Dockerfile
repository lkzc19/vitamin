FROM golang:latest
LABEL authors="lkzc19"

ENV TZ=Asia/Shanghai

WORKDIR /app

COPY . .

RUN go mod tidy && \
    go build -o app main.go

EXPOSE 3000

ENTRYPOINT ["./app"]
