package rest_wx

import (
	"testing"
)

func TestGetAccessToken(t *testing.T) {
	GetAccessToken(appid, secret)
}

func TestBatchGet(t *testing.T) {
	token := GetAccessToken(appid, secret)
	BatchGet(token)
}
