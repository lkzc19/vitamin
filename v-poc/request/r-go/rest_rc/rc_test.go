package rest_rc

import (
	"strconv"
	"sync"
	"testing"
)

func TestLoginAdmin(t *testing.T) {
	LoginAdmin()
}

var wg sync.WaitGroup

func TestPostMessage2NewUser(t *testing.T) {
	wg.Add(3)
	go test("f")
	go test("g")
	go test("h")
	wg.Wait()
}

func test(prefix string) {
	defer wg.Done()
	for i := range 1000 {
		PostMessage2NewUser("JbQtJXXGgvvdY6vcR", "NDBwYcz3vtNr0SactRDROpY0vsgW4334I5GgNL44qpX", prefix+strconv.Itoa(i))
	}
}

func TestPostMessage2OldUser(t *testing.T) {
	for range 100 {
		for j := range 100 {
			PostMessage2OldUser("SBcPhT95D9Ktb43uP", "ffu5yGP_oXwazKGcYz_R5_lnCtPb8jBGyXdUq-3RvKn", "b"+strconv.Itoa(j))
		}
	}
}
