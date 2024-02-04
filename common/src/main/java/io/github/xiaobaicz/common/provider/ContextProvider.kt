package io.github.xiaobaicz.common.provider

import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import io.github.xiaobaicz.common.app.Application.ActivityLifecycleCallbacksDefault
import io.github.xiaobaicz.common.spi.ApplicationLifecycleSpi

/**
 * Android Context提供者
 */
@AutoService(ApplicationLifecycleSpi::class)
class ContextProvider : ApplicationLifecycleSpi, ActivityLifecycleCallbacksDefault {

    companion object {

        @JvmStatic
        val applicationContext = ObjectProvider<Context>()

        @JvmStatic
        val visibleActivity = ObjectProvider<Activity>()

    }

    override fun onCreate(application: Application) {
        applicationContext.set(application)
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPostResumed(activity: Activity) {
        visibleActivity.set(activity)
    }

    override fun onActivityPrePaused(activity: Activity) {
        visibleActivity.clear()
    }

}