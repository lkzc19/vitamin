package main

import (
	"testing"
)

func TestInitData(t *testing.T) {
	var transactionRepo = TransactionRepo{db: db}
	transactionRepo.initData()
	transactionRepo.List()
}

func TestT1(t *testing.T) {
	var transactionRepo = TransactionRepo{db: db}
	transactionRepo.T1()
}

func TestT2(t *testing.T) {
	var transactionRepo = TransactionRepo{db: db}
	transactionRepo.T2()
}

func TestT3(t *testing.T) {
	var transactionRepo = TransactionRepo{db: db}
	transactionRepo.T3()
}

func TestT4(t *testing.T) {
	var transactionRepo = TransactionRepo{db: db}
	transactionRepo.T4()
}
