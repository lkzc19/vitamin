package main

import (
	"fmt"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel/contract"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel/models"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/officialAccount"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"io"
	"net/http"
	"os"
	"wx-gin/utils"
)

var OfficialAccountApp *officialAccount.OfficialAccount

func init() {
	err := godotenv.Load(".env")
	if err != nil {
		fmt.Println("开发需要[.env]配置文件, 线上不需要此文件，直接读取环境变量。")
	}
	appid := os.Getenv("WX_APPID")
	secret := os.Getenv("WX_SECRET")
	token := os.Getenv("WX_TOKEN")
	aeskey := os.Getenv("WX_AESKEY")
	OfficialAccountApp, err = officialAccount.NewOfficialAccount(&officialAccount.UserConfig{
		AppID:  appid,
		Secret: secret,
		Token:  token,
		AESKey: aeskey,

		Log: officialAccount.Log{
			Level: "debug",
			File:  "./wechat.log",
		},

		HttpDebug: true,
		Debug:     false,
	})
	utils.CheckErr(err)
}

func main() {
	r := gin.Default()
	r.GET("/ping", pingEndpoint)

	// 验证微信公众号Token
	r.GET("/", tokenEndpoint)
	r.POST("/", eventEndpoint)

	err := r.Run(":3000")
	utils.CheckErr(err)
}

func pingEndpoint(c *gin.Context) {
	c.JSON(200, gin.H{
		"message": "pong...",
	})
}

func tokenEndpoint(c *gin.Context) {
	rs, err := OfficialAccountApp.Server.VerifyURL(c.Request)
	if err != nil {
		panic(err)
	}
	text, _ := io.ReadAll(rs.Body)
	c.String(http.StatusOK, string(text))
}

func eventEndpoint(c *gin.Context) {
	rs, err := OfficialAccountApp.Server.Notify(c.Request, func(event contract.EventInterface) interface{} {
		fmt.Println("event", event)

		switch event.GetMsgType() {
		case models.CALLBACK_MSG_TYPE_TEXT:

		}

		// 这里回复success告诉微信我收到了，后续需要回复用户信息可以主动调发消息接口
		return kernel.SUCCESS_EMPTY_RESPONSE
	})
	utils.CheckErr(err)

	text, _ := io.ReadAll(rs.Body)
	c.String(http.StatusOK, string(text))
}
