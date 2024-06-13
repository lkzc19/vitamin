package main

import (
	"testing"
)

func TestInitData(t *testing.T) {
	var transactionRepo = TransactionRepo{DB: DB}
	transactionRepo.initData()
	transactionRepo.List()
}

func TestT1(t *testing.T) {
	var transactionRepo = TransactionRepo{DB: DB}
	transactionRepo.T1()
}

func TestT2(t *testing.T) {
	var transactionRepo = TransactionRepo{DB: DB}
	transactionRepo.T2()
}

func TestT3(t *testing.T) {
	var transactionRepo = TransactionRepo{DB: DB}
	transactionRepo.T3()
}

func TestT4(t *testing.T) {
	var transactionRepo = TransactionRepo{DB: DB}
	transactionRepo.T4()
}
