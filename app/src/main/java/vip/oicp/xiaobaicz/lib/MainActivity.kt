package vip.oicp.xiaobaicz.lib

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vip.oicp.xiaobaicz.lib.common.app.AppCompatActivity
import vip.oicp.xiaobaicz.lib.common.provider.ContextProvider
import vip.oicp.xiaobaicz.lib.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        lifecycleScope.launch {
            println(ContextProvider.applicationContext)
            println(ContextProvider.topActivity())
        }
    }

}
