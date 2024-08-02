package rest_wx

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

func TestGetAccessToken(t *testing.T) {
	GetAccessToken("wx6b1c0efdb36293ab", "a7c5d9c7610f0e15cd337a07c51a9c46")
	//83_TsGNGvB_3FSb9wPu7V_TynXBQBnR2z7E5eDuPRNRMXp4YpU6IJjhuJ9z4PcTLAKDnZ0mRK0yJ4GQBRIB4dh1VrX3aviqEcUumo0s30tVoF9cbJTcX0f-B9MjMgQVEXjABAYHA
}
