FROM golang:latest

WORKDIR /app
COPY . .

RUN go build -o app main.go

EXPOSE 3000

CMD [ "sh", "-c", "./app" ]
