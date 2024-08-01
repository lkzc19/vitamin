package rest_rc

import (
	"strconv"
	"testing"
)

func TestLoginAdmin(t *testing.T) {
	for range 100 {
		LoginAdmin()
	}
}

func TestPostMessage2NewUser(t *testing.T) {
	for i := range 1000 {
		PostMessage2NewUser("SBcPhT95D9Ktb43uP", "ffu5yGP_oXwazKGcYz_R5_lnCtPb8jBGyXdUq-3RvKn", "d"+strconv.Itoa(i))
	}
}

func TestPostMessage2OldUser(t *testing.T) {
	for range 100 {
		for j := range 100 {
			PostMessage2OldUser("SBcPhT95D9Ktb43uP", "ffu5yGP_oXwazKGcYz_R5_lnCtPb8jBGyXdUq-3RvKn", "b"+strconv.Itoa(j))
		}
	}
}
