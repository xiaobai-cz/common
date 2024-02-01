package io.github.xiaobaicz.common.spi

import android.app.Application
import android.content.Context

/**
 * Application生命周期监听，通过SPI注册
 * @see io.github.xiaobaicz.common.app.Application
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