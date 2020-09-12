package com.acorn.websocketdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mockHelper = MockSocketHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectBtn.setOnClickListener {
            mockHelper.startServer {
                SocketManager.url = it
                logI("url:$it")
                SocketManager.instance //初始化
            }
        }
        sendBtn.setOnClickListener {
            SocketManager.instance.send("hello")
        }
        closeBtn.setOnClickListener {
            //Code must be in range [1000,5000)
            SocketManager.instance.close(1003, "不玩了")
        }
    }
}