package vip.oicp.xiaobaicz.lib.common.app

import android.content.Context

interface ApplicationLifecycleSpi {

    fun onAttachBaseContext(context: Context) {}

    fun onCreate(application: Application)

}