package vip.oicp.xiaobaicz.lib.common.app

import android.content.Context

/**
 * Application生命周期监听，通过SPI注册
 * @see vip.oicp.xiaobaicz.lib.common.app.Application
 */
interface ApplicationLifecycleSpi {

    /**
     * Application附加BaseContext回调
     */
    fun onAttachBaseContext(context: Context) {}

    /**
     * Application#onCreate回调
     */
    fun onCreate(application: Application)

}