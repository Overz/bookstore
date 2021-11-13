GO=go
GOBUILD=$(GO) build
GOINSTALL=$(GO) install
GOTEST=$(GO) test
GO_BINDATA=go-bindata
TAGS=netgo
FLAGS=-tags $(TAGS)
LDFLAGS=-w -s -extldflags "-static"

CMDNAME=bookstore
SOURCES=$(shell find . -type f -name "*.go") go.mod

TOOLS_MARKER=.tools_ok
TOOLS_SOURCE=go.mod tools.go

ASSET_PKGNAME=migrations
ASSET_DIR=internal/migrations
ASSET_SOURCES=$(shell find $(ASSET_DIR) | grep sql)
ASSET_GO=$(ASSET_DIR)/bindata.go

AIRFLAG=-c
AIRCONFG=./bookstore.air.conf

GOFMTFLAGS=-w
GOFMTPATH=**/**/*.go

all: build

build: $(CMDNAME)

$(CMDNAME): $(SOURCES) $(ASSET_GO)
	$(GOBUILD) -o $@ $(FLAGS) -ldflags '$(LDFLAGS)' ./cmd/$(CMDNAME)

$(ASSET_GO): $(TOOLS_MARKER) $(ASSET_SOURCES)
	$(GO_BINDATA) -fs -pkg $(ASSET_PKGNAME) -ignore '.*.go' -prefix $(ASSET_DIR)/ -o $@ $(ASSET_DIR)

tools: $(TOOLS_MARKER)

$(TOOLS_MARKER): $(TOOLS_SOURCE)
	$(GOINSTALL) github.com/go-bindata/go-bindata/...
	touch $@

clean:
	rm -f $(CMDNAME) $(ASSET_GO) $(TOOLS_MARKER)

dev:
	air $(AIRFLAG) $(AIRCONFG)

demo:
	go run ./cmd/$(CMDNAME)/main.go

style:
	gofmt $(GOFMTFLAGS) $(GOFMTPATH)

restart:
	docker-compose down
	docker volume rm -f bookstore-postgres
	docker-compose up -d

.PHONY: all build tools clean