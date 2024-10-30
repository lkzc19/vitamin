package main

import (
	"fmt"
	"time"

	"github.com/go-co-op/gocron/v2"
)

func main() {
	// create a scheduler
	s, err := gocron.NewScheduler()
	if err != nil {
		checkErr(err)
	}

	// add a job to the scheduler
	j, err := s.NewJob(
		gocron.DurationJob(10*time.Second),
		gocron.NewTask(
			func(a string, b int) {
				fmt.Println(a, b)
			},
			"hello",
			1,
		),
	)
	if err != nil {
		checkErr(err)
	}
	// each job has a unique id
	fmt.Println(j.ID())

	// start the scheduler
	s.Start()

	// block until you are ready to shut down
	select {
	case <-time.After(time.Minute):
	}

	// when you're done, shut it down
	err = s.Shutdown()
	if err != nil {
		checkErr(err)
	}
}
