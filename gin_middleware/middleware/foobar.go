package middleware

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func Foo() gin.HandlerFunc {
	return func(c *gin.Context) {
		fmt.Println("--before Foo--")
		c.Next()
		fmt.Println("--after Foo--")
	}
}

func Bar() gin.HandlerFunc {
	return func(c *gin.Context) {
		fmt.Println("--before Bar--")
		c.Next()
		fmt.Println("--after Bar--")
	}
}
