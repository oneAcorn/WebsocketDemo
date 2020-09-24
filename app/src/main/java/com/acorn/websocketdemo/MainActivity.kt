package com.acorn.websocketdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acorn.websocketdemo.utils.MockSocketHelper
import com.acorn.websocketdemo.utils.TestWebSocketServerHelper
import com.acorn.websocketdemo.utils.logI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mockHelper = MockSocketHelper()
    private val mServerHelper = TestWebSocketServerHelper()

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

        connectBtn2.setOnClickListener {
            mServerHelper.start {
                logI("url:$it")
                SocketManager.url = it
                SocketManager.instance
            }
        }

        sendBtn.setOnClickListener {
            SocketManager.instance.send("hello")
        }
        closeBtn.setOnClickListener {
            //Code must be in range [1000,5000)
            SocketManager.instance.close(1003, "不玩了")
        }
        closeBtn2.setOnClickListener {
            mServerHelper.stop()
        }
        otherActivityBtn.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}