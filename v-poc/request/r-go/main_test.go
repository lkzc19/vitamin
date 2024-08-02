package main

import (
	"fmt"
	"os"
	"testing"
)

func TestEnv(t *testing.T) {
	appid := os.Getenv("WX_APPID")
	fmt.Println(appid)
	secret := os.Getenv("WX_SECRET")
	fmt.Println(secret)
}
