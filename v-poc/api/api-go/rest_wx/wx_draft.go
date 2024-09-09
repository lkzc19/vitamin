package rest_wx

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"r-go/utils"
)

type Draft struct {
	token string
}

// BatchGet https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Get_draft_list.html
func (r Draft) BatchGet() {
	client := &http.Client{}

	url := "https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=" + r.token
	data := map[string]any{
		"offset":     0,
		"count":      10,
		"no_content": 0,
	}
	reqBody, err := json.Marshal(data)
	utils.CheckErr(err)
	req, err := http.NewRequest("POST", url, bytes.NewBuffer(reqBody))
	utils.CheckErr(err)
	req.Header.Set("Content-Type", "application/json")
	resp, err := client.Do(req)
	utils.CheckErr(err)
	defer resp.Body.Close()
	respBody, err := io.ReadAll(resp.Body)
	utils.CheckErr(err)
	fmt.Println(string(respBody))
}
