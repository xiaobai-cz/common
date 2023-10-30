@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.provider

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.google.auto.service.AutoService
import vip.oicp.xiaobaicz.lib.common.app.Application.ActivityLifecycleCallbacksDefault
import vip.oicp.xiaobaicz.lib.common.spi.ApplicationLifecycleSpi
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * Android Context提供者
 */
@AutoService(ApplicationLifecycleSpi::class)
class ContextProvider : ApplicationLifecycleSpi, ActivityLifecycleCallbacksDefault {

    companion object {

        // Application onCreate 进行赋值
        @JvmStatic
        private var applicationContextOrNull: Context? = null

        @JvmStatic
        val applicationContext: Context get() = applicationContextOrNull ?: throw NullPointerException("application context is null")

        @JvmStatic
        private var topActivityContextRef: Reference<Context>? = null

        @JvmStatic
        val topActivityContext: Context? get() = topActivityContextRef?.get()

    }

    override fun onCreate(application: Application) {
        applicationContextOrNull = application
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        topActivityContextRef = WeakReference(activity)
    }

}