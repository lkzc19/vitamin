package rest_wx

import "testing"

func TestFreePublish_Submit(t *testing.T) {
	token := GetAccessToken(appid, secret)
	api := FreePublish{token: token}
	api.Submit("KaKnV-cSKLeSJ7yEvlCJwwWTh3soolHn80Rlki3aKHs12nUXvQZCHmiuXDDbg6sS")
}

func TestFreePublish_GetArticle(t *testing.T) {
	token := GetAccessToken(appid, secret)
	api := FreePublish{token: token}
	api.GetArticle("pLHetLtI4SobYKHF1J6qy-Wsn4PbstLqwxW33O-PhMzO-5jmapUtW6OyComt1p27")
}

func TestFreePublish_BatchGet(t *testing.T) {
	token := GetAccessToken(appid, secret)
	api := FreePublish{token: token}
	api.BatchGet()
}
