package main

import (
	"bytes"
	"compress/gzip"
	"encoding/base64"
	"fmt"
	"github.com/gin-gonic/gin"
	"io"
	"net/http"
)

type SensorsSdkData struct {
	Gzip     int    `json:"gzip" form:"gzip"`
	DataList string `json:"data_list" form:"data_list"`
}

func main() {
	gin.SetMode(gin.ReleaseMode)
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "pong",
		})
	})
	r.POST("/", func(c *gin.Context) {
		param := SensorsSdkData{}
		if err := c.ShouldBind(&param); err != nil {
			checkErr(err)
		}
		//fmt.Println(param)
		fmt.Println("解码前: " + param.DataList)
		// Base64 解码
		decodedData, err := base64.StdEncoding.DecodeString(param.DataList)
		checkErr(err)
		data, err := decompressData(decodedData)
		checkErr(err)
		fmt.Println("解码后: " + data)
		c.JSON(http.StatusOK, gin.H{
			"message": "pong",
		})
	})
	err := r.Run(":3000")
	checkErr(err)
}

// 解压缩 Gzip 数据为字符串
func decompressData(data []byte) (string, error) {
	reader, err := gzip.NewReader(bytes.NewReader(data))
	if err != nil {
		return "", err
	}
	defer reader.Close()

	var buf bytes.Buffer
	_, err = io.Copy(&buf, reader)
	if err != nil {
		return "", err
	}

	return buf.String(), nil
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
