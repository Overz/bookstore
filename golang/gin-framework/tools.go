// +build tools

package bookstore

// Now, just got get your tools and add them to the import list.
// The ones above are just samples so gofmt doesn't remove the empty import.
//
// If you DON'T remove them, they will be added to your module dependencies!

import (
	_ "github.com/go-bindata/go-bindata"
)
