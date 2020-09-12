package com.acorn.websocketdemo

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/9/12.
 */
class SocketManager private constructor() {
    private val client = OkHttpClient.Builder()
        .pingInterval(60L, TimeUnit.SECONDS) //心跳间隔
        .build()

    companion object {
        lateinit var url: String

        val instance: WebSocket by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SocketManager().getSocket() }
    }

    private fun getSocket(): WebSocket {
        val request = Request.Builder().url(url).build()
        return client.newWebSocket(request, MySocketListener())
    }
}