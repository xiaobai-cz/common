package vip.oicp.xiaobaicz.lib.common.provider

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import vip.oicp.xiaobaicz.lib.common.spi.ApplicationLifecycleSpi

/**
 * Android Context提供者
 */
interface ContextProvider {

    /**
     * Application onCreate 后有效
     */
    val applicationContext: Context

    companion object : ContextProvider {

        // Application onCreate 进行赋值
        private var applicationContextOrNull: Context? = null

        override val applicationContext: Context
            get() = applicationContextOrNull ?: throw NullPointerException("application context is null")

    }

    @AutoService(ApplicationLifecycleSpi::class)
    class ContextProviderRegister : ApplicationLifecycleSpi {
        override fun onCreate(application: Application) {
            applicationContextOrNull = application
        }
    }

}