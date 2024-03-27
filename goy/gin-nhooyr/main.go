package main

import (
	"context"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"nhooyr.io/websocket"
	"nhooyr.io/websocket/wsjson"
	"time"
)

func main() {
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})
	r.GET("/ws", websocketHandler)
	err := r.Run()
	if err != nil {
		return
	} // 监听并在 0.0.0.0:8080 上启动服务
}

func websocketHandler(c *gin.Context) {
	// 将HTTP连接升级为WebSocket连接
	conn, err := websocket.Accept(c.Writer, c.Request, nil)
	if err != nil {
		// 处理错误
		c.AbortWithError(http.StatusInternalServerError, err)
		return
	}
	defer conn.Close(websocket.StatusInternalError, "内部出错了")
	ctx, cancel := context.WithTimeout(c.Request.Context(), time.Second*10)
	defer cancel()

	var v interface{}
	err = wsjson.Read(ctx, conn, &v)
	if err != nil {
		log.Println(err)
		return
	}
	log.Printf("接收到客户端: %v\n", v)

	err = wsjson.Write(ctx, conn, "Hello WebSocket Client")
	if err != nil {
		log.Println(err)
		return
	}
	conn.Close(websocket.StatusNormalClosure, "")
}
