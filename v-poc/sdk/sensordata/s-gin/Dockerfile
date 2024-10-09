FROM golang:1.22

WORKDIR /app

COPY . .
RUN go mod tidy && go build -o main .

EXPOSE 3000

ENTRYPOINT ["./main"]
