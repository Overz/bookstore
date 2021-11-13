package config

import (
	"fmt"
	"os"
	"path"
	"reflect"
	"strconv"

	"github.com/Overz/book-store/validate"
	"github.com/joho/godotenv"
)

type constants struct {
	DEBUG    bool
	CWD      string
	PORT     string
	DB_URL   string
	GIN_MODE string
}

var Constants *constants = &constants{
	DEBUG:    false,
	CWD:      "",
	PORT:     "",
	DB_URL:   "",
	GIN_MODE: "",
}

// automatization for get environments,
// just add the environment in "Constants",
// or if need it
func GetConstants() error {
	cwd, err := os.Getwd()
	if err != nil {
		return fmt.Errorf("error getting 'cwd', %v", err)
	}
	Constants.CWD = path.Join(cwd)

	_ = godotenv.Load(".env")
	// if err := godotenv.Load(path.Join(cwd, ".env")); err != nil {
	// return fmt.Errorf("error opening '.env' file, %v", err)
	// }

	// using reflect to interact with the struct
	s := reflect.Indirect(reflect.ValueOf(Constants))

	// should skip some environment
	skip := []string{"CWD"}

	// loop in all field of struct
	for i := 0; i < s.NumField(); i++ {
		// get the field name
		name := s.Type().Field(i).Name

		if validate.Contains(skip, name) {
			continue
		}

		// check if is valid
		field := s.FieldByName(name)
		if !field.IsValid() || !field.CanSet() {
			continue
		}

		// load the env value
		env := os.Getenv(name)
		if env == "" {
			return fmt.Errorf("'%s' is required but was not passed", name)
		}

		// if the struct field is string, can set the value
		if field.Kind() == reflect.String {
			field.SetString(env)
			continue
		}

		if field.Kind() == reflect.Bool {
			b, err := strconv.ParseBool(env)
			if err != nil {
				return fmt.Errorf("error parsing '%s' to boolean", err)
			}

			field.SetBool(b)
			continue
		}

		if field.Kind() == reflect.Int {
			v, e := strconv.ParseInt(env, 10, 64)
			if e != nil {
				return fmt.Errorf("error casting env '%s' to integer", name)
			}

			field.SetInt(v)
			continue
		}
	}

	return nil
}
