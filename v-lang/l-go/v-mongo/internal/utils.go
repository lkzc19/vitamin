package internal

func CheckErr(err error) {
	if err != nil {
		panic(err)
	}
}
