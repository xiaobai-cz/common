package io.github.xiaobaicz.demo

import android.os.Bundle
import androidx.activity.addCallback
import io.github.xiaobaicz.common.app.AppCompatActivity
import io.github.xiaobaicz.common.content.finish
import io.github.xiaobaicz.common.content.parseData

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 接受传输数据
        println(intent.parseData<String>())

        onBackPressedDispatcher.addCallback {
            // 返回数据
            finish(RESULT_OK, "随便返回什么都行")
        }

    }

}