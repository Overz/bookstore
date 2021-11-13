package database

import (
	"fmt"
	"strconv"

	"github.com/Overz/book-store/models"
)

type RDMBSBookStore interface {
	FindOneBook(string) (models.Book, error)
	CreateBook(models.Book) (models.Book, error)
	UpdateBook(models.Book) error
	DeleteBook(string) error
	ListBooks(models.Book, string, string) (Pagination, error)
}

func (p *Database) countBooks() (int, error) {
	rows, err := p.db.Query("SELECT COUNT(*) as total FROM book")
	if err != nil {
		fmt.Println("[DB-BOOK] Error counting books: ", err)
		return 0, err
	}
	defer rows.Close()

	count := 0
	for rows.Next(){
		rows.Scan(&count);
	}

	return count, nil
}

func (p *Database) FindOneBook(id string) (models.Book, error) {
	rows, err := p.db.Query("SELECT * FROM book WHERE cdBook = ($1)", id)
	if err != nil {
		return models.Book{}, err
	}
	defer rows.Close()

	b := models.Book{}
	for rows.Next() {
		if err := rows.Scan(&b.CdBook, &b.NmAuthor, &b.NmBook, &b.DeDescription, &b.DtCreated); err != nil {
			return b, err
		}
	}

	return b, nil
}

func (p *Database) CreateBook(b models.Book) (models.Book, error) {
	if _, err := p.db.Query(`
		INSERT INTO book (cdBook, nmBook, nmAuthor, deDescription, dtCreated)
		VALUES ($1, $2, $3, $4, $5)
	`, b.CdBook, b.NmBook, b.NmAuthor, b.DeDescription, b.DtCreated); err != nil {
		fmt.Println("[DB-BOOk] Error creating book: ", err)
		return models.Book{}, err
	}

	return b, nil
}

func (p *Database) UpdateBook(b models.Book) error {
	if _, err := p.db.Query(`UPDATE book SET nmBook = $1, nmAuthor = $2, deDescription = $3 WHERE cdBook = $4`,
		b.NmBook, b.NmAuthor, b.DeDescription, b.CdBook); err != nil {
		fmt.Println("[DB-BOOk] Error updating book: ", err)
		return err
	}

	return nil
}

func (p *Database) DeleteBook(id string) error {
	_, err := p.db.Query(`DELETE FROM book WHERE cdBook = $1`, id)
	if err != nil {
		fmt.Println("[DB-BOOk] Error deleting book: ", err)
		return err
	}

	return nil
}

func (p *Database) ListBooks(b models.Book, page string, pageSize string) (Pagination, error) {
	var params []interface{}
	query := "SELECT * FROM book"

	if page == "" {
		page = "0"
	}

	if pageSize == "" {
		pageSize = "20"
	}

	// create a filter if exists book values
	if (models.Book{}) != b {
		query, params = createFilter(b, query, params)
	}

	query += fmt.Sprintf(" LIMIT $%d OFFSET $%d ", len(params)+1, len(params)+2)
	params = append(params, pageSize, page)

	p1, _ := strconv.Atoi(page)
	p2, _ := strconv.Atoi(pageSize)
	pag := Pagination{Data: []interface{}{}, Page: p1, PageSize: p2}

	result, err := p.db.Queryx(query, params...)
	if err != nil {
		fmt.Println("[DB-BOOK] Error executing query: ", err)
		return pag, err
	}

	defer result.Close()

	var books []interface{}
	for result.Next() {
		b := models.Book{CdBook: "", NmAuthor: "", NmBook: "", DeDescription: "", DtCreated: 0}

		if err := result.Scan(&b.CdBook, &b.NmAuthor, &b.NmBook, &b.DeDescription, &b.DtCreated); err != nil {
			fmt.Println("[DB-BOOK] Error scaning the list results")
			return pag, err
		}

		books = append(books, b)
	}

	totalRecords, err := p.countBooks()
	if err != nil {
		return pag, err
	}

	if books == nil {
		books = []interface{}{}
	}

	pag.Data = books
	pag.Page = p1
	pag.PageSize = p2
	pag.TotalRecords = totalRecords

	return pag, nil
}

func createFilter(b models.Book, query string, params []interface{}) (string, []interface{}) {
	first := true
	query += " WHERE "
	or := " OR "

	if b.DeDescription != "" {
		if !first {
			query += or
		}

		params = append(params, b.DeDescription)
		query += fmt.Sprintf(" deDescription ILIKE '%%'||$%d||'%%' ", len(params))
		first = false
	}

	if b.NmAuthor != "" {
		if !first {
			query += or
		}

		params = append(params, b.NmAuthor)
		query += fmt.Sprintf(" nmAuthor ILIKE '%%'||$%d||'%%' ", len(params))
		first = false
	}

	if b.NmBook != "" {
		if !first {
			query += or
		}

		params = append(params, b.NmBook)
		query += fmt.Sprintf(" nmBook ILIKE '%%'||$%d||'%%' ", len(params))
		first = false
	}

	return query, params
}
