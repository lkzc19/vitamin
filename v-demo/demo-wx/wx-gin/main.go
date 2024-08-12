package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"wx-gin/endpoint"
	"wx-gin/utils"
)

func init() {
	err := godotenv.Load(".env")
	if err != nil {
		fmt.Println("开发需要[.env]配置文件, 线上不需要此文件，直接读取环境变量(docker读线上的[.env]文件)。")
	}
}

func main() {
	r := gin.Default()

	r.GET("/ping", endpoint.Ping)

	// 验证微信公众号Token
	r.GET("/", endpoint.Token)
	r.POST("/", endpoint.Event)

	err := r.Run(":3000")
	utils.CheckErr(err)
}
