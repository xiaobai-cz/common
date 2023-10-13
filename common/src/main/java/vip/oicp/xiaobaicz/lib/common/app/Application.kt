package vip.oicp.xiaobaicz.lib.common.app

import android.app.Application
import android.content.Context
import vip.oicp.xiaobaicz.lib.common.utils.LanguageUtils

abstract class Application : Application() {

    override fun attachBaseContext(base: Context?) {
        val context = LanguageUtils.handleBaseApplicationContext(base)
        super.attachBaseContext(context)
    }

}