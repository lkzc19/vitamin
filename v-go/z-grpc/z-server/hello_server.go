package main

import (
	"context"
	"log"
	pb "z-proto"
)

type HelloServer struct {
	pb.UnimplementedGreeterServer
}

func (HelloServer) SayHello(_ context.Context, in *pb.HelloRequest) (*pb.HelloReply, error) {
	log.Printf("Received: %v", in.GetName())
	return &pb.HelloReply{Message: "Hello " + in.GetName()}, nil
}
