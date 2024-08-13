package rest_wx

import (
	"testing"
)

func TestGetAccessToken(t *testing.T) {
	GetAccessToken(appid, secret)
}

func TestGetArticle(t *testing.T) {
	token := GetAccessToken(appid, secret)
	GetArticle(token, "pLHetLtI4SobYKHF1J6qy-Wsn4PbstLqwxW33O-PhMzO-5jmapUtW6OyComt1p27")
}

func TestBatchGet(t *testing.T) {
	token := GetAccessToken(appid, secret)
	BatchGet(token)
}

func TestDraftBatchGet(t *testing.T) {
	token := GetAccessToken(appid, secret)
	DraftBatchGet(token)
}
