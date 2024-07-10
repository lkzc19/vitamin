package main

import (
	"fmt"
	"testing"
)

func TestAddColumn(t *testing.T) {
	var schemaService = SchemaService{db: DB}
	schemaService.AddColumn("prop_sd_os")
}

func TestAddColumnBySql(t *testing.T) {
	var schemaService = SchemaService{db: DB}
	schemaService.AddColumnBySql("prop_order_time", Timestamp)
}

func TestHasColumn(t *testing.T) {
	var schemaService = SchemaService{db: DB}
	fmt.Println(schemaService.HasColumn("prop_sd_os"))
}
