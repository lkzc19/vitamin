package model

import (
	"gorm.io/gorm"
)

type Foo struct {
	gorm.Model
	Name string `json:"name"`
	Num  int64  `json:"num"`
}

func (Foo) TableName() string {
	return "v_foo"
}
