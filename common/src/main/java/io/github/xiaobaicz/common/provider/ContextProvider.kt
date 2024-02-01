@file:Suppress("unused")

package io.github.xiaobaicz.common.provider

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import com.google.auto.service.AutoService
import io.github.xiaobaicz.common.app.Application.ActivityLifecycleCallbacksDefault
import io.github.xiaobaicz.common.spi.ApplicationLifecycleSpi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

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
        private val topActivityProvider = ObjectProvider<Activity>()

        @[JvmStatic MainThread]
        fun topActivity(block: (Activity) -> Unit) {
            topActivityProvider.get(block)
        }

        @[JvmStatic MainThread]
        suspend fun topActivity(): Activity = suspendCancellableCoroutine { c ->
            topActivityProvider.get { obj ->
                c.resume(obj)
            }
        }

    }

    override fun onCreate(application: Application) {
        applicationContextOrNull = application
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPostResumed(activity: Activity) {
        topActivityProvider.set(activity)
    }

    override fun onActivityPrePaused(activity: Activity) {
        topActivityProvider.clear()
    }

}