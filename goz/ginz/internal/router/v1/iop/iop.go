/*gin 出入参(in/out parameter)测试*/

package iop

import (
	"ginz/internal/service"
	"github.com/gin-gonic/gin"
)

func Router(r *gin.RouterGroup) {
	iopService := service.ProviderIopService()

	func(r *gin.RouterGroup) {
		r.GET("/testEnum", iopService.TestEnum)
	}(r.Group("/iop"))
}
