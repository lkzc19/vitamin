package main

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"go.mongodb.org/mongo-driver/mongo/readpref"
	"v-mongo/internal"
)

var db *mongo.Database

func init() {
	uri := "mongodb://localhost:23017"
	dbName := "vitamin"
	client, err := mongo.Connect(context.Background(), options.Client().ApplyURI(uri))
	internal.CheckErr(err)
	err = client.Ping(context.Background(), readpref.Primary())
	internal.CheckErr(err)
	db = client.Database(dbName)
}

func main() {
	fmt.Println(db.Name())
}
