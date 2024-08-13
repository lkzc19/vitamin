package rest_wx

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"r-go/utils"
)

// https://api.weixin.qq.com/cgi-bin/freepublish/submit?access_token=ACCESS_TOKEN

// GetArticle https://developers.weixin.qq.com/doc/offiaccount/Publish/Get_article_from_id.html
func GetArticle(token, articleId string) {
	client := &http.Client{}

	url := "https://api.weixin.qq.com/cgi-bin/freepublish/getarticle?access_token=" + token
	data := map[string]any{
		"article_id": articleId,
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

// BatchGet https://developers.weixin.qq.com/doc/offiaccount/Publish/Get_publication_records.html
func BatchGet(token string) {
	client := &http.Client{}

	url := "https://api.weixin.qq.com/cgi-bin/freepublish/batchget?access_token=" + token
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
