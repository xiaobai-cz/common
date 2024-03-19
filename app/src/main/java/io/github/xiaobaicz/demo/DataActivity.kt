package io.github.xiaobaicz.demo

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import io.github.xiaobaicz.common.content.finishAndResult
import io.github.xiaobaicz.common.content.parseData
import io.github.xiaobaicz.common.log.log

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 接受传输数据
        log(intent.parseData<String>())

        onBackPressedDispatcher.addCallback {
            // 返回数据
            finishAndResult(RESULT_OK, "随便返回什么都行")
        }

    }

}