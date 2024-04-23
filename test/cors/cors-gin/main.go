package main

import (
	"fmt"
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()

	type CorsBug struct {
		Name string `json:"name"`
	}
	r.POST("/cors-bug", func(c *gin.Context) {
		var corsBug CorsBug
		if err := c.ShouldBindJSON(&corsBug); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		fmt.Println(corsBug.Name)
		c.JSON(http.StatusOK, "cors-bug")
	})

	// 添加跨域处理插件
	r.Use(cors.New(cors.Config{
		AllowAllOrigins: true,
		AllowMethods:    []string{"GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"},
		AllowHeaders:    []string{"Origin", "Content-Length", "Content-Type", "X-Nahida"},
	}))

	r.GET("/ping", func(c *gin.Context) {
		fmt.Println("ping...pong")
		c.JSON(http.StatusOK, "pong")
	})

	type Normal struct {
		Name string `json:"name"`
	}
	r.POST("/normal", func(c *gin.Context) {
		var normal Normal
		if err := c.ShouldBindJSON(&normal); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		fmt.Println(normal.Name)
		c.JSON(http.StatusOK, "bar")
	})

	type XNahida struct {
		Name string `json:"name"`
	}
	r.POST("/XNahida", func(c *gin.Context) {
		var xNahida XNahida
		if err := c.ShouldBindJSON(&xNahida); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		fmt.Println(xNahida.Name)
		c.JSON(http.StatusOK, "bar")
	})

	type XHutao struct {
		Name string `json:"name"`
	}
	r.POST("/XHutao", func(c *gin.Context) {
		var xHutao XHutao
		if err := c.ShouldBindJSON(&xHutao); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		fmt.Println(xHutao.Name)
		c.JSON(http.StatusOK, "bar")
	})

	_ = r.Run(":4998") // 监听并在 0.0.0.0:8080 上启动服务
}
