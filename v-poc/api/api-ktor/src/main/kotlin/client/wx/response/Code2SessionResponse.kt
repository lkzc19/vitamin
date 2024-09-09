package org.example.client.wx.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Code2SessionResponse @JvmOverloads constructor(
    @JsonProperty("session_key")
    var sessionKey: String = "",
    var unionid: String = "",
    var openid: String = "",
    var errmsg: String = "",
    var errcode: Int = 0,
    var rid: String = "",
)