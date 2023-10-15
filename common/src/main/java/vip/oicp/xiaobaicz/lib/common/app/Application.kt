package vip.oicp.xiaobaicz.lib.common.app

import android.app.Application
import android.content.Context
import vip.oicp.xiaobaicz.lib.common.utils.LanguageUtils
import vip.oicp.xiaobaicz.lib.common.utils.catchOnly

/**
 * Application基类
 * 1. 集成语言工具
 * 2. 集成ApplicationLifecycleSpi
 */
abstract class Application : Application() {

    override fun attachBaseContext(base: Context?) {
        val context = LanguageUtils.handleBaseApplicationContext(base)
        super.attachBaseContext(context)
        context ?: return
        ApplicationLifecycleSpi.spiList.forEach {
            catchOnly {
                it.onAttachBaseContext(context)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationLifecycleSpi.spiList.forEach {
            catchOnly {
                it.onCreate(this)
            }
        }
    }

}