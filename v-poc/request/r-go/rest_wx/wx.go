package rest_wx

import (
	"encoding/json"
	"github.com/joho/godotenv"
	"io"
	"net/http"
	"os"
	"r-go/utils"
)

var appid string
var secret string

func init() {
	err := godotenv.Load("../.env")
	utils.CheckErr(err)
	appid = os.Getenv("WX_APPID")
	secret = os.Getenv("WX_SECRET")
}

// GetAccessToken https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
func GetAccessToken(appid, secret string) string {
	url := "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret
	resp, err := http.Get(url)
	utils.CheckErr(err)
	defer resp.Body.Close()
	respBody, err := io.ReadAll(resp.Body)
	utils.CheckErr(err)
	data := map[string]any{}
	err = json.Unmarshal(respBody, &data)
	utils.CheckErr(err)
	return data["access_token"].(string)
}
