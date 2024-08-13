package endpoint

import (
	"fmt"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel/contract"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel/messages"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/kernel/models"
	"github.com/ArtisanCloud/PowerWeChat/v3/src/officialAccount"
	"github.com/gin-gonic/gin"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"io"
	"net/http"
	"os"
	"strings"
	"wx-gin/model"
	"wx-gin/utils"
)

var officialAccountApp *officialAccount.OfficialAccount

func init() {
	appid := os.Getenv("WX_APPID")
	secret := os.Getenv("WX_SECRET")
	token := os.Getenv("WX_TOKEN")
	aeskey := os.Getenv("WX_AESKEY")
	var err error
	officialAccountApp, err = officialAccount.NewOfficialAccount(&officialAccount.UserConfig{
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

	db, err := gorm.Open(sqlite.Open("vitamin.db"), &gorm.Config{})
	utils.CheckErr(err)
	err = db.AutoMigrate(&model.Article{})
	utils.CheckErr(err)
}

func Ping(c *gin.Context) {
	c.JSON(200, gin.H{
		"message": "pong...",
	})
}

func Token(c *gin.Context) {
	rs, err := officialAccountApp.Server.VerifyURL(c.Request)
	if err != nil {
		panic(err)
	}
	text, _ := io.ReadAll(rs.Body)
	c.String(http.StatusOK, string(text))
}

func Event(c *gin.Context) {
	rs, err := officialAccountApp.Server.Notify(c.Request, func(event contract.EventInterface) interface{} {
		fmt.Println("=== event start ===")
		fmt.Println("GetToUserName", event.GetToUserName())
		fmt.Println("GetFromUserName", event.GetFromUserName())
		fmt.Println("GetCreateTime", event.GetCreateTime())
		fmt.Println("GetMsgType", event.GetMsgType())
		fmt.Println("GetEvent", event.GetEvent())
		fmt.Println("GetChangeType", event.GetChangeType())
		fmt.Println("event", event.GetEvent())
		fmt.Println("GetContent", string(event.GetContent()))
		fmt.Println("=== event end ===")

		fmt.Println("=== msgType start ===")
		switch event.GetMsgType() {
		case models.CALLBACK_MSG_TYPE_EVENT:
			if strings.ToLower(event.GetEvent()) == "subscribe" {
				fmt.Println("subscribe")
			} else if strings.ToLower(event.GetEvent()) == "unsubscribe" {
				fmt.Println("unsubscribe")
			} else if strings.ToLower(event.GetEvent()) == "publishjobfinish" {
				msg := messages.Article{}
				err := event.ReadMessage(&msg)
				utils.CheckErr(err)
				fmt.Println(msg)
			} else {
				fmt.Println(event.GetEvent())
			}
		}
		fmt.Println("=== msgType end ===")

		// 这里回复success告诉微信我收到了，后续需要回复用户信息可以主动调发消息接口
		return kernel.SUCCESS_EMPTY_RESPONSE
	})
	utils.CheckErr(err)

	text, _ := io.ReadAll(rs.Body)
	c.String(http.StatusOK, string(text))
}
