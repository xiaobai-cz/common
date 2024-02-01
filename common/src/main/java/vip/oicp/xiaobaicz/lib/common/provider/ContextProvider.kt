@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.provider

import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vip.oicp.xiaobaicz.lib.common.app.Application.ActivityLifecycleCallbacksDefault
import vip.oicp.xiaobaicz.lib.common.spi.ApplicationLifecycleSpi
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

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

        // 有效范围在 onResumed - onPaused 之间
        @JvmStatic
        private var topActivityRef: Reference<Activity>? = null

        // topActivity 锁
        @JvmStatic
        private val topActivityLock: ReentrantLock by lazy { ReentrantLock(true) }

        // topActivity 锁同步状态
        @JvmStatic
        private val topActivityCond: Condition by lazy { topActivityLock.newCondition() }

        @JvmStatic
        suspend fun topActivity(): Activity = withContext(Dispatchers.IO) {
            topActivityLock.lock()
            var top = topActivityRef?.get()
            try {
                while (top == null) {
                    topActivityCond.await()
                    top = topActivityRef?.get()
                }
                top
            } finally {
                topActivityLock.unlock()
            }
        }

    }

    override fun onCreate(application: Application) {
        applicationContextOrNull = application
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPostResumed(activity: Activity) {
        topActivityLock.withLock {
            topActivityRef = WeakReference(activity)
            topActivityCond.signalAll()
        }
    }

    override fun onActivityPrePaused(activity: Activity) {
        topActivityLock.withLock {
            topActivityRef?.clear()
            topActivityRef = null
        }
    }

}