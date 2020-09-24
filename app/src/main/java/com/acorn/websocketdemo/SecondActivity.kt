package com.acorn.websocketdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

/**
 * Created by acorn on 2020/9/12.
 */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        sendBtn.setOnClickListener {
            SocketManager.instance.socket.send("hi")
        }
    }
}