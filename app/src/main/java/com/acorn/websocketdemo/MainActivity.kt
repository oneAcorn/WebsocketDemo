package com.acorn.websocketdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acorn.websocketdemo.utils.MockSocketHelper
import com.acorn.websocketdemo.utils.TestWebSocketServerHelper
import com.acorn.websocketdemo.utils.logI
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MainActivity : AppCompatActivity() {
    private val mockHelper = MockSocketHelper()
    private val mServerHelper = TestWebSocketServerHelper()
    private val listener = MyListenr()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectBtn.setOnClickListener {
            mockHelper.startServer {
                SocketManager.url = it
                log("Url:$it")
                logI("url:$it")
                //初始化
                SocketManager.instance.addListener(listener)
//                SocketManager.instance.socket
            }
        }

        connectBtn2.setOnClickListener {
            mServerHelper.start {
                logI("url:$it")
                log("Url:$it")
                SocketManager.url = it
                SocketManager.instance.addListener(listener)
//                SocketManager.instance.socket
            }
        }

        connectBtn3.setOnClickListener {
            SocketManager.url = addrEt.text.toString()
            SocketManager.instance.addListener(listener)
//            SocketManager.instance
        }

        sendBtn.setOnClickListener {
            SocketManager.instance.socket.send("hello")
        }
        closeBtn.setOnClickListener {
            //Code must be in range [1000,5000)
            SocketManager.instance.socket.close(1003, "不玩了")
        }
        closeBtn2.setOnClickListener {
            mServerHelper.stop()
        }
        otherActivityBtn.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    inner class MyListenr : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            log(text)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            log("reason:$reason,code:$code")
        }
    }

    fun log(msg: String) {
        tv.text = "${tv.text}\n$msg"
    }
}