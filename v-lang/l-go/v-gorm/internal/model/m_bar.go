package model

import (
	"gorm.io/gorm"
)

type Bar struct {
	gorm.Model
	Name string `json:"name"`
	Num  int64  `json:"num"`
}

func (Bar) TableName() string {
	return "v_bar"
}
