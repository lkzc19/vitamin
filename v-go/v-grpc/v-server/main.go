package main

import (
	"fmt"
	"google.golang.org/grpc"
	"log"
	"net"
	pb "v-proto"
)

func main() {
	lis, err := net.Listen("tcp", fmt.Sprintf("localhost:%d", 3000))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	var opts []grpc.ServerOption
	grpcServer := grpc.NewServer(opts...)
	pb.RegisterGreeterServer(grpcServer, &HelloServer{})
	_ = grpcServer.Serve(lis)
}
