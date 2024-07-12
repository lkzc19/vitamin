package main

import (
	"fmt"
	"strings"
	"testing"
)

func TestAddColumn(t *testing.T) {
	var schemaService = SchemaService{db: db}
	schemaService.AddColumn("prop_sd_os")
}

func TestAddColumnBySql(t *testing.T) {
	var schemaService = SchemaService{db: db}
	schemaService.AddColumnBySql("prop_product_text", Text)
	schemaService.AddColumnBySql("prop_product_timestamp", Timestamp)
	schemaService.AddColumnBySql("prop_product_numeric", Numeric)
	schemaService.AddColumnBySql("prop_product_bool", Bool)
}

func TestAddColumnBySql_case(t *testing.T) {
	var schemaService = SchemaService{db: db}
	schemaService.AddColumnBySql("prop_product_case", Text)
	fmt.Println("=== vitamin ok 1 ===")
	schemaService.AddColumnBySql("prop_product_case", Text)
	fmt.Println("=== vitamin ok 2 ===")
	schemaService.AddColumnBySql("prop_product_Case", Text)
}

func TestToLower(t *testing.T) {
	fmt.Println(strings.ToLower("ABCdefg!@#456"))
}

func TestTableColumn(t *testing.T) {
	var schemaService = SchemaService{db: db}
	schemaService.TableColumn()
}

func TestHasColumn(t *testing.T) {
	var schemaService = SchemaService{db: db}
	fmt.Println(schemaService.HasColumn("prop_sd_os"))
}
