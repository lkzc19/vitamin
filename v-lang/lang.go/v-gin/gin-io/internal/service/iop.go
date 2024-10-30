package service

import (
	"gin-io/internal/dto/iop"
	"github.com/gin-gonic/gin"
	"net/http"
)

type IopService struct{}

var instanceIopService = IopService{}

func ProviderIopService() IopService {
	return instanceIopService
}

func (s IopService) TestEnum(c *gin.Context) {
	state, err := iop.CheckState(c.Query("state"))
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.JSON(200, gin.H{"state": state})
}
