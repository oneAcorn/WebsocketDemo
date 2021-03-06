package com.acorn.websocketdemo.utils

import com.acorn.websocketdemo.MyWebSocketServer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.InetAddress
import java.net.InetSocketAddress

/**
 * Created by acorn on 2020/9/24.
 */
class TestWebSocketServerHelper {
    private var server: MyWebSocketServer? = null

    fun start(onStartCallback: (url: String) -> Unit) {
        val port = 43496
        val disposable = Observable.create<InetAddress> {
            it.onNext(NetUtils.getLocalIPAddress())
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                server = MyWebSocketServer(InetSocketAddress(it, port))
                server?.start()
                onStartCallback("ws:${it.hostAddress}:$port")
            }
    }

    fun stop() {
        server?.stop()
    }
}