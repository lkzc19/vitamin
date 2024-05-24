package main

import (
	"fmt"
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type TransactionRepo struct {
	DB *gorm.DB
}

func (r TransactionRepo) initData() {
	fooList := []*model.Foo{
		{Name: "foo1", Num: 18},
		{Name: "foo2", Num: 19},
	}
	result := r.DB.Create(fooList)
	internal.CheckErr(result.Error)
	fmt.Println("foo 插入数量: $d", result.RowsAffected)

	barList := []*model.Bar{
		{Name: "bar1", Num: 99},
		{Name: "bar2", Num: 87},
	}
	result = r.DB.Create(barList)
	internal.CheckErr(result.Error)
	fmt.Println("bar 插入数量: $d", result.RowsAffected)
}

func (r TransactionRepo) List() {
	var fooList []model.Foo
	result := r.DB.Find(&fooList)
	internal.CheckErr(result.Error)
	for _, it := range fooList {
		fmt.Println(it)
	}

	var barList []model.Bar
	result = r.DB.Find(&barList)
	internal.CheckErr(result.Error)
	for _, it := range barList {
		fmt.Println(it)
	}
}

// T1 测试事务
func (r TransactionRepo) T1() {
	err = r.DB.Transaction(func(tx *gorm.DB) error {
		// 在事务中执行一些 db 操作（从这里开始，您应该使用 'tx' 而不是 'db'）
		if err = tx.Create(&model.Foo{Name: "foo", Num: 42}).Error; err != nil {
			// 返回任何错误都会回滚事务
			return err
		}

		if err = tx.Create(&model.Bar{Name: "bar", Num: 42}).Error; err != nil {
			return err
		}

		return nil
	})
	internal.CheckErr(err)
}

// T2 测试事务 回滚
func (r TransactionRepo) T2() {
	err = r.DB.Transaction(func(tx *gorm.DB) error {
		// 在事务中执行一些 db 操作（从这里开始，您应该使用 'tx' 而不是 'db'）
		if err = tx.Create(&model.Foo{Name: "foo", Num: 42}).Error; err != nil {
			// 返回任何错误都会回滚事务
			return err
		}

		return internal.VitaminErr
	})
	internal.CheckVitaminErr(err)
}

// T3 测试事务 嵌套事务
func (r TransactionRepo) T3() {
	err = r.DB.Transaction(func(tx *gorm.DB) error {
		tx.Create(&model.Foo{Name: "xxx", Num: 42})

		err = tx.Transaction(func(tx2 *gorm.DB) error {
			tx2.Create(&model.Foo{Name: "yyy", Num: 42})
			return internal.VitaminErr
		})
		internal.CheckVitaminErr(err)

		err = tx.Transaction(func(tx3 *gorm.DB) error {
			tx3.Create(&model.Foo{Name: "zzz", Num: 42})
			return nil
		})
		internal.CheckVitaminErr(err)

		return nil
	})
	internal.CheckVitaminErr(err)
}

// T4 测试事务 手动事务
func (r TransactionRepo) T4() {
	tx := r.DB.Begin()
	tx.Create(&model.Foo{Name: "foo1", Num: 18})

	tx.SavePoint("sp1")
	tx.Create(&model.Foo{Name: "foo2", Num: 19})
	tx.RollbackTo("sp1")

	tx.Commit()
}
