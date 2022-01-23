package cli

import (
	"context"
	"fmt"
	"net/http"
	"time"

	"github.com/Overz/book-store/config"
	"github.com/gin-gonic/gin"
)

var App *gin.Engine

func Server(ctx context.Context, cancel context.CancelFunc) <-chan struct{} {
	fmt.Println("[APP] Starting...")
	appCh := make(chan struct{})

	select {
	case <-ctx.Done():
		fmt.Printf("[APP] Stopping server...")
		close(appCh)
		return appCh
	default:
	}

	gin.SetMode(config.Constants.GIN_MODE)
	App = gin.Default()

	ConfigRoutes(App.Group("/api"))

	server := http.Server{
		Addr:    config.Constants.PORT,
		Handler: App,
	}

	go func() {
		fmt.Printf("[APP] Running on port %s\n", config.Constants.PORT)
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			fmt.Println("[APP] Error starting application", err)
			
			cancel()
		}
	}()

	go func() {
		<-ctx.Done()
		shutdownCtx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
		defer cancel()

		if err := server.Shutdown(shutdownCtx); err != nil {
			fmt.Printf("[APP] error shutting down server: %v", err)
		}

		fmt.Println("[APP] HTTP Server stopped")
		close(appCh)
	}()

	return appCh
}
