package routes

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/Overz/book-store/internal/database"
	"github.com/Overz/book-store/internal/middlewares"
	"github.com/Overz/book-store/models"
	"github.com/gin-gonic/gin"
	gonanoid "github.com/matoous/go-nanoid"
)

func getBody(c *gin.Context) models.Book {
	b := models.Book{}
	data, _ := c.GetRawData()
	_ = json.Unmarshal(data, &b)

	return b
}

func getQuery(c *gin.Context) (b models.Book, page string, pageSize string) {
	b = models.Book{
		CdBook:        c.Query("id"),
		NmAuthor:      c.Query("author"),
		NmBook:        c.Query("book"),
		DeDescription: c.Query("description"),
	}

	page = c.Query("page")
	pageSize = c.Query("pageSize")

	return b, page, pageSize
}

// get specific book
func GetBookRouter(c *gin.Context) {
	book, e := database.Db.Book.FindOneBook(c.Param("cdBook"))
	if e != nil {
		middlewares.BadRequestError(c, []string{"internal server error"})
		return
	}

	if (models.Book{}) == book {
		middlewares.NotFound(c)
		return
	}

	c.JSON(http.StatusOK, book)
}

// create new book
func CreateBookRouter(c *gin.Context) {
	b := getBody(c)

	errs := []string{}
	if b.DeDescription == "" {
		errs = append(errs, "description is required")
	}

	if b.NmAuthor == "" {
		errs = append(errs, "author is required")
	}

	if b.NmBook == "" {
		errs = append(errs, "book is required")
	}

	if len(errs) > 0 {
		middlewares.BadRequestError(c, errs)
		return
	}

	id, err := gonanoid.Nanoid(models.PK_BOOK_SIZE)
	if err != nil {
		middlewares.InternalServerError(c)
	}
	b.CdBook = id
	b.DtCreated = time.Now().UnixNano() / 1e6

	b, err = database.Db.Book.CreateBook(b)
	if err != nil {
		middlewares.InternalServerError(c)
	}

	c.JSON(http.StatusCreated, b)
}

// update a book
func UpdateBook(c *gin.Context) {
	b, err := database.Db.Book.FindOneBook(c.Param("cdBook"))

	if err != nil {
		middlewares.InternalServerError(c)
		return
	}

	if (models.Book{}) == b {
		middlewares.NotFound(c)
		return
	}

	body := getBody(c)
	if body.NmAuthor != "" {
		b.NmAuthor = body.NmAuthor
	}

	if body.NmBook != "" {
		b.NmBook = body.NmBook
	}

	if body.DeDescription != "" {
		b.DeDescription = body.DeDescription
	}

	if err := database.Db.Book.UpdateBook(b); err != nil {
		middlewares.InternalServerError(c)
		return
	}

	c.JSON(http.StatusNoContent, struct{}{})
}

// delete one book
func DeleteBook(c *gin.Context) {
	if err := database.Db.Book.DeleteBook(c.Param("cdBook")); err != nil {
		middlewares.InternalServerError(c)
		return
	}

	c.JSON(http.StatusNoContent, struct{}{})
}

// list all books with pagination
func ListBooks(c *gin.Context) {
	b, page, pageSize := getQuery(c)

	list, err := database.Db.Book.ListBooks(b, page, pageSize)

	if err != nil {
		middlewares.InternalServerError(c)
		return
	}

	c.JSON(http.StatusOK, list)
}
