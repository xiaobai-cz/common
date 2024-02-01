package io.github.xiaobaicz.common.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import io.github.xiaobaicz.common.utils.LanguageUtils

/**
 * Activity基类
 * 1. 集成语言工具
 */
abstract class AppCompatActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val context = LanguageUtils.handleBaseContext(newBase)
        super.attachBaseContext(context)
    }

    override fun onRestart() {
        super.onRestart()
        LanguageUtils.onRestart(this)
    }

}