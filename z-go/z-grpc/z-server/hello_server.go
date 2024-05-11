package main

import (
	"context"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	hello "z-proto"
)

type HelloServer struct {
}

func (HelloServer) SayHello(context.Context, *hello.HelloRequest) (*hello.HelloReply, error) {
	return nil, status.Errorf(codes.Unimplemented, "method SayHello not implemented")
}
