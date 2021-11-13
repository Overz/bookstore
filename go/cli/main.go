package cli

import (
	"context"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"github.com/jmoiron/sqlx"

	// needed to use the postgres driver for connection
	"github.com/Overz/book-store/config"
	"github.com/Overz/book-store/internal/database"
	"github.com/Overz/book-store/internal/migrations"
	_ "github.com/lib/pq"
)

var db *sqlx.DB;

func Start() int {
	var err error

	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	// graceful exit
	exitCh := make(chan os.Signal, 1)
	signal.Notify(exitCh, syscall.SIGINT, syscall.SIGTERM)
	go Finish(exitCh, ctx, cancel)

	// load all environments
	if err = config.GetConstants(); err != nil {
		fmt.Println(err.Error())
		return 1
	}
	
	fmt.Println("[APP] Connection to database...")
	// open postgres connection
	db, err = sqlx.Open("postgres", config.Constants.DB_URL)
	if err != nil {
		fmt.Println(err.Error())
		return 1;
	}

	// use the implementation RDMBS
	database.Db = database.NewPostgres(db)

	// run migrations and define the migrations path and read all files
	if err = migrations.Migrate(db); err != nil {
		fmt.Println("[CONNECTION] Error running migrations", err);
		return 1;
	}

	// start the server http
	appCh := Server(ctx, cancel)
	
	<-ctx.Done()
	fmt.Println("[APP] Closing context...")
	<-appCh
	fmt.Println("[APP] Shutdown complete!")
	return 0
}

func Finish(quit chan os.Signal, ctx context.Context, cancel context.CancelFunc) {
	select {
	case <-quit:
		fmt.Println("[APP] Received shutdown signal")
		_ = db.Close()
		cancel()
		return
	case <-ctx.Done():
		return
	}
}
