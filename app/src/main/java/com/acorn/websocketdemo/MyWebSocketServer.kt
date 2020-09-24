package com.acorn.websocketdemo

import com.acorn.websocketdemo.utils.logI
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import java.nio.ByteBuffer

/**
 * Created by acorn on 2020/9/24.
 */
class MyWebSocketServer(addr: InetSocketAddress) : WebSocketServer(addr) {

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        logI("Server onOpen")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        logI("Server onClose")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        logI("Server onMessage $message")
        conn?.send("server $message")
    }

    override fun onMessage(conn: WebSocket?, message: ByteBuffer?) {
        super.onMessage(conn, message)
        logI("Server onMessage ByteBuffer ${message?.let { String(it.array()) }}")
        conn?.send("....")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        logI("Server onError")
    }

    override fun onStart() {
        logI("Server onStart")
    }
}