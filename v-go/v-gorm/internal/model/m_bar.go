package model

import (
	"github.com/shopspring/decimal"
	"gorm.io/gorm"
)

type Bar struct {
	gorm.Model
	Bar          string          `json:"foo" gorm:"type:varchar(255);not null;uniqueIndex:ufo"`
	ChannelPrice decimal.Decimal `json:"channelPrice" gorm:"type:decimal(10,2);not null"`
}
