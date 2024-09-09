package rest_wx

import "testing"

func TestDraft_BatchGet(t *testing.T) {
	token := GetAccessToken(appid, secret)
	api := Draft{token: token}
	api.BatchGet()
}
