package vip.oicp.xiaobaicz.lib

import android.os.Bundle
import vip.oicp.xiaobaicz.lib.common.app.AppCompatActivity
import vip.oicp.xiaobaicz.lib.common.provider.ContextProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(ContextProvider.applicationContext)
    }

}
