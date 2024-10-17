package main

import (
	"os"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": os.Getenv("EXAMPLE"),
		})
	})
	r.Run(":3000") // listen and serve on 0.0.0.0:8080
}