package models

const PK_BOOK_SIZE = 10

type Book struct {
	CdBook        string `json:"id,omitempty" db:"cdbook"`
	NmAuthor      string `json:"author,omitempty" db:"nmauthor"`
	NmBook        string `json:"book,omitempty" db:"nmbook"`
	DeDescription string `json:"description,omitempty" db:"dedescription"`
	DtCreated     int64  `json:"created,omitempty" db:"dtcreated"`
}
