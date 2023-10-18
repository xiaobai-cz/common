package vip.oicp.xiaobaicz.lib.common.spi

import android.app.Application
import android.content.Context
import vip.oicp.xiaobaicz.lib.common.utils.catchOnly
import java.util.ServiceLoader

object ApplicationLifecycle {

    @JvmStatic
    val spiList: List<ApplicationLifecycleSpi> by lazy {
        catchOnly(emptyList()) {
            val clazz = ApplicationLifecycleSpi::class.java
            ServiceLoader.load(clazz, clazz.classLoader).toList()
        }
    }

    @JvmStatic
    fun attachBaseContext(context: Context?) {
        context ?: return
        spiList.forEach {
            catchOnly {
                it.onAttachBaseContext(context)
            }
        }
    }

    @JvmStatic
    fun onCreate(application: Application) {
        spiList.forEach {
            catchOnly {
                it.onCreate(application)
            }
        }
    }

}