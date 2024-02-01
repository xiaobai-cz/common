package io.github.xiaobaicz.common.spi

import android.app.Application
import android.content.Context
import io.github.xiaobaicz.common.utils.tryCatch
import java.util.ServiceLoader

object ApplicationLifecycle {

    @JvmStatic
    val spiList: List<ApplicationLifecycleSpi> by lazy {
        tryCatch(emptyList()) {
            val clazz = ApplicationLifecycleSpi::class.java
            ServiceLoader.load(clazz, clazz.classLoader).toList()
        }.value
    }

    @JvmStatic
    fun attachBaseContext(context: Context?) {
        context ?: return
        spiList.forEach {
            tryCatch {
                it.onAttachBaseContext(context)
            }
        }
    }

    @JvmStatic
    fun onCreate(application: Application) {
        spiList.forEach {
            tryCatch {
                it.onCreate(application)
            }
        }
    }

}