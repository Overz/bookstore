GO=go
GOBUILD=$(GO) build
GOINSTALL=$(GO) install
GOTEST=$(GO) test
GO_BINDATA=go-bindata
FLAGS=
LDFLAGS=-w -s

CMDNAME=bookstore
SOURCES=$(shell find . -type f -name "*.go") go.mod

ASSET_PKGNAME=migrations
ASSET_DIR=internal/migrations
ASSET_SOURCES=$(shell find $(ASSET_DIR) | grep sql)
ASSET_GO=$(ASSET_DIR)/bindata.go

all: build

build: $(CMDNAME)
	$(GOBUILD) -o $@ $(FLAGS) -ldflags '$(LDFLAGS)' ./cmd/$(CMDNAME)

.PHONY: all build tools clean
