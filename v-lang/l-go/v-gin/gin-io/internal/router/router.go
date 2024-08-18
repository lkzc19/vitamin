package router

import (
	"gin-io/internal/router/v1/iop"
	"gin-io/internal/router/v1/ping"
	"github.com/gin-gonic/gin"
)

func BuildRouter(r *gin.Engine) {
	v1(r.Group("/api/v1"))
}

func v1(r *gin.RouterGroup) {
	ping.Router(r)
	iop.Router(r)
}
