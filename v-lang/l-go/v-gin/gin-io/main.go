package main

import (
	"gin-io/internal/router"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	router.BuildRouter(r)
	_ = r.Run(":3000") // listen and serve on 0.0.0.0:8080 (for windows "localhost:8080")
}
