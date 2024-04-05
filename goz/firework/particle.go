package main

type Lifecycle = string

const (
	ALIVE     Lifecycle = "ALIVE"
	DECLINING Lifecycle = "DECLINING"
	DYING     Lifecycle = "DYING"
	DEAD      Lifecycle = "DEAD"
)
