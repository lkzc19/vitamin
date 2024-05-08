package iop

import "errors"

type State string

const (
	Active   State = "active"
	Inactive State = "inactive"
)

func CheckState(state string) (State, error) {
	switch state {
	case "active":
		return Active, nil
	case "inactive":
		return Inactive, nil
	default:
		return "", errors.New("invalid state")
	}
}
