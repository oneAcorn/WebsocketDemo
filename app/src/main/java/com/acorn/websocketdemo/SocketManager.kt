package com.acorn.websocketdemo

import com.acorn.websocketdemo.utils.logI
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/9/12.
 */
class SocketManager private constructor() {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) //连接超时
        .pingInterval(60L, TimeUnit.SECONDS) //心跳间隔
        .retryOnConnectionFailure(true)
        .addInterceptor { chain ->
            var request = chain.request()
            logI("Interceptor 执行了? ${request.url()}")
            chain.proceed(request)
        }
        .build()

    companion object {
        lateinit var url: String

        val instance: WebSocket by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SocketManager().getSocket() }
    }

    private fun getSocket(): WebSocket {
        val request = Request.Builder()
            .url(url)
            .header("token", "mockToken")
            .header("userId", "acorn")
            .build()
        return client.newWebSocket(request, MySocketListener())
    }
}