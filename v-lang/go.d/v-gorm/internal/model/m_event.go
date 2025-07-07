package model

import "gorm.io/gorm"

type Event struct {
	gorm.Model
	Name string `json:"name"`
}

func (Event) TableName() string {
	return "v_event"
}
