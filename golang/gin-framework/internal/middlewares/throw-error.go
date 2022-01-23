package middlewares

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type response struct {
	Message string   `json:"message"`
	Errors  []string `json:"errors,omitempty"`
}

func BadRequestError(c *gin.Context, e []string) {
	l := len(e)

	if l <= 0 {
		c.JSON(http.StatusBadRequest, map[string]string{"message": e[0]})
		return
	}

	resp := response{}
	if l > 0 {
		resp.Message = e[0]
		for i := 0; i < l; i++ {
			resp.Errors = append(resp.Errors, e[i])
		}
	}

	c.JSON(http.StatusBadRequest, resp)
}

func NotAuthorizedError(c *gin.Context) {
	c.JSON(http.StatusUnauthorized, map[string]string{"message": "Not Authorized"})
}

func NotFound(c *gin.Context) {
	c.JSON(http.StatusNotFound, map[string]string{"message": "Not found"})
}

func InternalServerError(c *gin.Context) {
	c.JSON(http.StatusInternalServerError, map[string]string{"message": "Internal server error"})
}
