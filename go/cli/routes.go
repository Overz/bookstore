package cli

import (
	"github.com/Overz/book-store/routes"
	"github.com/gin-gonic/gin"
)

func ConfigRoutes(router *gin.RouterGroup) {
	router.GET("/book/:cdBook", routes.GetBookRouter)
	router.POST("/book", routes.CreateBookRouter)
	router.PUT("/book/:cdBook", routes.UpdateBook)
	router.GET("/book", routes.ListBooks)
	router.DELETE("/book/:cdBook", routes.DeleteBook)
}
