package rest_rc

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"r-go/utils"
)

var baseURL = "http://192.168.0.134:4000/"

func LoginAdmin() {
	// 登陆管理员
	loginURL := baseURL + "/api/v1/login"
	var data = map[string]any{
		"user":     "CKMRO.Bot",
		"password": "111111",
	}
	reqBody, err := json.Marshal(data)
	utils.CheckErr(err)
	loginResp, err := http.Post(loginURL, "application/json", bytes.NewBuffer(reqBody))
	utils.CheckErr(err)
	defer loginResp.Body.Close()
	respBody, err := io.ReadAll(loginResp.Body)
	utils.CheckErr(err)
	data = map[string]any{}
	err = json.Unmarshal(respBody, &data)
	utils.CheckErr(err)
	data = data["data"].(map[string]any)
	userId := data["userId"].(string)
	authToken := data["authToken"].(string)
	fmt.Println("userId:", userId)
	fmt.Println("authToken:", authToken)
}

func PostMessage2NewUser(userId, authToken, username string) {
	client := &http.Client{}

	// 创建用户
	usersCreateURL := baseURL + "/api/v1/users.create"
	data := map[string]any{
		"email":    username + "@abc.com",
		"name":     username,
		"password": "111",
		"username": username,
	}
	reqBody, err := json.Marshal(data)
	utils.CheckErr(err)
	usersCreateReq, err := http.NewRequest("POST", usersCreateURL, bytes.NewBuffer(reqBody))
	utils.CheckErr(err)
	usersCreateReq.Header.Set("Content-Type", "application/json")
	usersCreateReq.Header.Add("X-User-Id", userId)
	usersCreateReq.Header.Add("X-Auth-Token", authToken)
	usersCreateResp, err := client.Do(usersCreateReq)
	utils.CheckErr(err)
	defer usersCreateResp.Body.Close()
	respBody, err := io.ReadAll(usersCreateResp.Body)
	utils.CheckErr(err)
	//fmt.Println(string(respBody))
	//fmt.Println(usersCreateResp.StatusCode)

	// 发送消息
	chatPostMessageURL := baseURL + "/api/v1/chat.postMessage"
	data = map[string]any{
		"roomId": "@" + username,
		"text":   "好耶!",
	}
	reqBody, err = json.Marshal(data)
	utils.CheckErr(err)
	chatPostMessageReq, err := http.NewRequest("POST", chatPostMessageURL, bytes.NewBuffer(reqBody))
	utils.CheckErr(err)
	chatPostMessageReq.Header.Set("Content-Type", "application/json")
	chatPostMessageReq.Header.Add("X-User-Id", userId)
	chatPostMessageReq.Header.Add("X-Auth-Token", authToken)
	chatPostMessageResp, err := client.Do(chatPostMessageReq)
	utils.CheckErr(err)
	defer chatPostMessageResp.Body.Close()

	respBody, err = io.ReadAll(chatPostMessageResp.Body)
	utils.CheckErr(err)

	fmt.Println(string(respBody))
}

func PostMessage2OldUser(userId, authToken, username string) {
	client := &http.Client{}

	// 发送消息
	chatPostMessageURL := baseURL + "/api/v1/chat.postMessage"
	data := map[string]any{
		"roomId": "@" + username,
		"text":   "好耶!",
	}
	reqBody, err := json.Marshal(data)
	utils.CheckErr(err)
	chatPostMessageReq, err := http.NewRequest("POST", chatPostMessageURL, bytes.NewBuffer(reqBody))
	utils.CheckErr(err)
	chatPostMessageReq.Header.Set("Content-Type", "application/json")
	chatPostMessageReq.Header.Add("X-User-Id", userId)
	chatPostMessageReq.Header.Add("X-Auth-Token", authToken)
	chatPostMessageResp, err := client.Do(chatPostMessageReq)
	utils.CheckErr(err)
	defer chatPostMessageResp.Body.Close()
	respBody, err := io.ReadAll(chatPostMessageResp.Body)
	utils.CheckErr(err)
	fmt.Println(string(respBody))
}
