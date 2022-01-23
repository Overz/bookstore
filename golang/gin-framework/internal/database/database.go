package database

import (
	"github.com/jmoiron/sqlx"
)

type Pagination struct {
	Data         []interface{} `json:"data"`
	Page         int           `json:"page"`
	PageSize     int           `json:"pageSize"`
	TotalRecords int           `json:"totalRecords"`
}

type RDMBSStore struct {
	Book RDMBSBookStore
}

type Database struct {
	db *sqlx.DB
}

var Db RDMBSStore

func NewPostgres(db *sqlx.DB) RDMBSStore {
	conn := &Database{db: db}

	return RDMBSStore{
		Book: conn,
	}
}
