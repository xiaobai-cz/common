package io.github.xiaobaicz.common.log

import com.google.auto.service.AutoService
import android.util.Log as ALog

@AutoService(Log::class)
class AndroidLog : Log {

    override fun log(obj: Any?) {
        ALog.d(Log.tag, obj.toString())
    }

    override fun log(format: String, vararg args: Any?) {
        ALog.d(Log.tag, String.format(format, *args))
    }

    override fun log(t: Throwable) {
        ALog.e(Log.tag, t.message, t)
    }

    override fun log(t: Throwable, format: String, vararg args: Any?) {
        ALog.e(Log.tag, String.format(format, *args), t)
    }

}