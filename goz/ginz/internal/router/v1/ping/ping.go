package ping

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func Router(r *gin.RouterGroup) {
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "pong",
		})
	})
}
