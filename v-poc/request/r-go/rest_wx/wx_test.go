package rest_wx

import (
	"testing"
)

func TestGetAccessToken(t *testing.T) {
	GetAccessToken(appid, secret)
}

func TestGetArticle(t *testing.T) {
	token := GetAccessToken(appid, secret)
	GetArticle(token, "oNop06PKCeZZ0D-aYse3MQwAkf04")
}

func TestBatchGet(t *testing.T) {
	token := GetAccessToken(appid, secret)
	BatchGet(token)
}
