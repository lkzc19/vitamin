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
	r.Use(cors.Default())

	r.GET("/ping", func(c *gin.Context) {
		fmt.Println("ping...pong")
		c.JSON(http.StatusOK, "pong")
	})

	type Foo struct {
		Name string `json:"name"`
	}
	r.POST("/foo", func(c *gin.Context) {
		var foo Foo
		if err := c.ShouldBindJSON(&foo); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		fmt.Println(foo.Name)
		c.JSON(http.StatusOK, "bar")
	})

	_ = r.Run(":3000") // 监听并在 0.0.0.0:8080 上启动服务
}
