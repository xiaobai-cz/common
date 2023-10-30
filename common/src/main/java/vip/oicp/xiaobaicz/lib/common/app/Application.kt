package vip.oicp.xiaobaicz.lib.common.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
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

    interface ActivityLifecycleCallbacksDefault : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

}