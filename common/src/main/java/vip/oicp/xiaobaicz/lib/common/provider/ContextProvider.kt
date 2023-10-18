@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.provider

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import vip.oicp.xiaobaicz.lib.common.spi.ApplicationLifecycleSpi

/**
 * Android Context提供者
 */
@AutoService(ApplicationLifecycleSpi::class)
class ContextProvider : ApplicationLifecycleSpi {

    companion object {

        // Application onCreate 进行赋值
        @JvmStatic
        private var applicationContextOrNull: Context? = null

        @JvmStatic
        val applicationContext: Context get() = applicationContextOrNull ?: throw NullPointerException("application context is null")

    }

    override fun onCreate(application: Application) {
        applicationContextOrNull = application
    }

}