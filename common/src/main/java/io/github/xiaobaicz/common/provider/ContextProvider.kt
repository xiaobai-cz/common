package io.github.xiaobaicz.common.provider

import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import io.github.xiaobaicz.common.app.ActivityLifecycleCallbacksDefault
import io.github.xiaobaicz.initializer.Initializer

/**
 * Android Context提供者
 */
@AutoService(Initializer::class)
class ContextProvider : Initializer, ActivityLifecycleCallbacksDefault {

    companion object {

        @JvmStatic
        val applicationContext = ObjectProvider<Context>()

        @JvmStatic
        val visibleActivity = ObjectProvider<Activity>()

    }

    override fun onInit(context: Context) {
        if (context !is Application) throw RuntimeException()
        applicationContext.set(context)
        context.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPostResumed(activity: Activity) {
        visibleActivity.set(activity)
    }

    override fun onActivityPrePaused(activity: Activity) {
        visibleActivity.clear()
    }

}