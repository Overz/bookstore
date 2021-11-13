package migrations

import (
	"fmt"
	"sort"
	"strings"

	"github.com/jmoiron/sqlx"
)

func Migrate(db *sqlx.DB) error {
	if _, err := db.Exec("CREATE TABLE IF NOT EXISTS migrations(name TEXT NOT NULL PRIMARY KEY)"); err != nil {
		return err
	}

	rows, err := db.Query("SELECT * FROM migrations")
	if err != nil {
		return err
	}
	defer rows.Close()

	errRunningMigrations := "error running migrations: %w"

	// create a hash-table
	m := make(map[string]struct{})
	for rows.Next() {
		var name string
		// set the name of row in variable 'name'
		if err := rows.Scan(&name); err != nil {
			return fmt.Errorf(errRunningMigrations, err)
		}

		// set in hash-table the script name
		m[name] = struct{}{}
	}

	if err := rows.Err(); err != nil {
		return fmt.Errorf(errRunningMigrations, err)
	}

	// migrations assets
	migrations := AssetNames()
	sort.Strings(migrations)

	// start transaction
	transaction, err := db.Begin()
	if err != nil {
		return fmt.Errorf("error starting migrations transaction %w", err)
	}

	for _, migration := range migrations {
		if _, ok := m[migration]; ok {
			fmt.Printf("[CONNECTION] Skipping migration %s\n", migration)
			continue
		}

		b, err := Asset(migration)
		if err != nil {
			return fmt.Errorf("[CONNECTION] Error reading content of %s", migration)
		}

		commands := strings.Split(string(b), ";")
		for _, cmd := range commands {
			cmd = strings.TrimSpace(cmd)

			if cmd == "" {
				continue
			}

			if _, err := transaction.Exec(cmd); err != nil {
				if err := transaction.Rollback(); err != nil {
					fmt.Println("[CONNECTION] Error, rollback: ", err)
				}

				return fmt.Errorf(errRunningMigrations, err)
			}
		}

		fmt.Println("[CONNECTION] Running migration: ", migration)
		if _, err := db.Exec("INSERT INTO migrations (name) VALUES ($1)", migration); err != nil {
			if err := transaction.Rollback(); err != nil {
				fmt.Println("[CONNECTION], Error, rollback: ", err)
			}

			return err
		}

		m[migration] = struct{}{}
	}

	if err := transaction.Commit(); err != nil {
		return fmt.Errorf("error commiting transaction %w", err)
	}

	return nil
}
