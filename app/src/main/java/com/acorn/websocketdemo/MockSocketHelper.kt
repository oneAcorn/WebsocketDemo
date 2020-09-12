package com.acorn.websocketdemo

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

/**
 * Created by acorn on 2020/9/12.
 */
class MockSocketHelper {
    private var mockWebServer: MockWebServer? = null

    private fun initMockServer() {
        if (mockWebServer != null)
            return
        mockWebServer = MockWebServer()
        mockWebServer?.enqueue(MockResponse().withWebSocketUpgrade(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                // 有客户端连接时回调
                logI(
                    "onOpen thead:${Thread.currentThread()},userId:${response.header("userId")}" +
                            ",token:${response.header("token")}"
                )
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                // 收到新消息时回调
                logI("onMessage:$text")
                when (text) {
                    "hello" -> {
                        send("hello client", webSocket)
                    }
                    else -> {
                        send("...", webSocket)
                    }
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                // 客户端主动关闭时回调
                logI("onClosing code:$code,reason:$reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                // WebSocket 连接关闭
                logI("onClosed code:$code,reason:$reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                // 出错了
                logI("onFailure err:${t.message}")
            }
        }))
    }

    fun send(msg: String, webSocket: WebSocket) {
        Thread {
            Thread.sleep(2000)
            webSocket.send(msg)
        }.start()
    }

    fun startServer(onStartCallback: (url: String) -> Unit) {
        initMockServer()
        val disposable = Observable.just(mockWebServer)
            .map {
                "ws:${mockWebServer?.hostName}:${mockWebServer?.port}"
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onStartCallback(it)
            }
    }
}