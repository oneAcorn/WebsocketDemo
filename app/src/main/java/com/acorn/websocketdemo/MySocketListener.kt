package com.acorn.websocketdemo

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * Created by acorn on 2020/9/12.
 */
class MySocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        // 有客户端连接时回调
        logI("client onOpen")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        // 收到新消息时回调
        logI("client onMessage:$text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        // 客户端主动关闭时回调
        logI("client onClosing code:$code,reason:$reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        // WebSocket 连接关闭
        logI("client onClosed code:$code,reason:$reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        // 出错了
        logI("client onFailure err:${t.message}")
    }
}