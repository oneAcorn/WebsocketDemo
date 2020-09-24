package com.acorn.websocketdemo

import com.acorn.websocketdemo.utils.logI
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * Created by acorn on 2020/9/12.
 */
class MySocketListener : WebSocketListener() {
    private val listeners: MutableList<WebSocketListener> = mutableListOf()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        listeners.forEach {
            it.onOpen(webSocket, response)
        }
        // 有客户端连接时回调
        logI("client onOpen")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        listeners.forEach {
            it.onMessage(webSocket, text)
        }
        // 收到新消息时回调
        logI("client onMessage:$text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        listeners.forEach {
            it.onClosing(webSocket, code, reason)
        }
        // 客户端主动关闭时回调
        logI("client onClosing code:$code,reason:$reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        listeners.forEach {
            it.onClosed(webSocket, code, reason)
        }
        // WebSocket 连接关闭
        logI("client onClosed code:$code,reason:$reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        listeners.forEach {
            it.onFailure(webSocket, t, response)
        }
        // 出错了
        logI("client onFailure err:${t.message}")
    }

    fun addListener(listener: WebSocketListener) {
        listeners.add(listener)
    }
}