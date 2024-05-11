package router

import (
	"ginz/internal/router/v1/iop"
	"ginz/internal/router/v1/ping"
	"github.com/gin-gonic/gin"
)

func BuildRouter(r *gin.Engine) {
	v1(r.Group("/api/v1"))
}

func v1(r *gin.RouterGroup) {
	ping.Router(r)
	iop.Router(r)
}
