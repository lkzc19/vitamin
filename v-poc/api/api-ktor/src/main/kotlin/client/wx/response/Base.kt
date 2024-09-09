package org.example.client.wx.response

abstract class Base(
    open var errmsg: String,
    open var errcode: Int,
    open var rid: String
)