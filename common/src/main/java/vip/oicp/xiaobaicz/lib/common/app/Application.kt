package vip.oicp.xiaobaicz.lib.common.app

import android.app.Application
import android.content.Context
import vip.oicp.xiaobaicz.lib.common.utils.LanguageUtils
import java.util.ServiceLoader

abstract class Application : Application() {

    private val applicationLifecycleSpiList: List<ApplicationLifecycleSpi> by lazy {
        try {
            val clazz = ApplicationLifecycleSpi::class.java
            ServiceLoader.load(clazz, clazz.classLoader).toList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

    override fun attachBaseContext(base: Context?) {
        val context = LanguageUtils.handleBaseApplicationContext(base)
        super.attachBaseContext(context)
        context ?: return
        applicationLifecycleSpiList.forEach {
            try {
                it.onAttachBaseContext(context)
            } catch (t: Throwable) { t.printStackTrace() }
        }
    }

    override fun onCreate() {
        super.onCreate()
        applicationLifecycleSpiList.forEach {
            try {
                it.onCreate(this)
            } catch (t: Throwable) { t.printStackTrace() }
        }
    }

}