package vip.oicp.xiaobaicz.lib.common.app

import android.app.Application
import android.content.Context
import vip.oicp.xiaobaicz.lib.common.spi.ApplicationLifecycle
import vip.oicp.xiaobaicz.lib.common.utils.LanguageUtils

/**
 * Application基类
 * 1. 集成语言工具
 * 2. 集成ApplicationLifecycleSpi
 */
abstract class Application : Application() {

    override fun attachBaseContext(base: Context?) {
        val context = LanguageUtils.handleBaseApplicationContext(base)
        super.attachBaseContext(context)
        ApplicationLifecycle.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationLifecycle.onCreate(this)
    }

}