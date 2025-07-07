package internal

import "errors"

func CheckErr(err error) {
	if err != nil {
		panic(err)
	}
}

func CheckVitaminErr(err error) {
	if errors.Is(err, VitaminErr) {
		println(err.Error())
	} else {
		CheckErr(err)
	}
}
