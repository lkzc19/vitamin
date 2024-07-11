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
	schemaService.AddColumnBySql("prop_product_text", Text)
	schemaService.AddColumnBySql("prop_product_timestamp", Timestamp)
	schemaService.AddColumnBySql("prop_product_numeric", Numeric)
	schemaService.AddColumnBySql("prop_product_bool", Bool)
}

func TestTableColumn(t *testing.T) {
	var schemaService = SchemaService{db: DB}
	schemaService.TableColumn()
}

func TestHasColumn(t *testing.T) {
	var schemaService = SchemaService{db: DB}
	fmt.Println(schemaService.HasColumn("prop_sd_os"))
}
