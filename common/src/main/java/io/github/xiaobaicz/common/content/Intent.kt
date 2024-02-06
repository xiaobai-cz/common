package io.github.xiaobaicz.common.content

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import io.github.xiaobaicz.common.app.Application.ActivityLifecycleCallbacksDefault
import io.github.xiaobaicz.common.provider.ContextProvider
import io.github.xiaobaicz.common.spi.ApplicationLifecycleSpi
import java.util.UUID

private val dataCacheMap = HashMap<String, Any>()

const val KEY_DATA = "data"

fun putCache(obj: Any): String {
    val key = UUID.randomUUID().toString()
    dataCacheMap[key] = obj
    return key
}

private fun del(key: String) {
    dataCacheMap.remove(key)
}

fun <T> Activity.parseData(): T? {
    val key = intent.getStringExtra(KEY_DATA) ?: return null
    val obj = dataCacheMap[key] ?: return null
    @Suppress("UNCHECKED_CAST")
    return obj as T
}

inline fun <reified A : Activity> toActivity(obj: Any? = null) {
    ContextProvider.visibleActivity.get {
        it.startActivity(create<A>(it, obj))
    }
}

inline fun <reified A : Activity> create(context: Context, obj: Any? = null): Intent {
    val intent = Intent(context, A::class.java)
    obj?.also {
        intent.putExtra(KEY_DATA, putCache(it))
    }
    return intent
}

@AutoService(ApplicationLifecycleSpi::class)
class DataCacheHandler : ApplicationLifecycleSpi, ActivityLifecycleCallbacksDefault {

    override fun onCreate(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        val key = activity.intent.getStringExtra(KEY_DATA) ?: return
        del(key)
    }

}
