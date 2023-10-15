package vip.oicp.xiaobaicz.lib.common.app

import android.content.Context
import vip.oicp.xiaobaicz.lib.common.utils.catchOnly
import java.util.ServiceLoader

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

    companion object {
        @JvmStatic
        val spiList: List<ApplicationLifecycleSpi> by lazy {
            catchOnly(emptyList()) {
                val clazz = ApplicationLifecycleSpi::class.java
                ServiceLoader.load(clazz, clazz.classLoader).toList()
            }
        }
    }

}