package io.github.xiaobaicz.demo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.xiaobaicz.common.app.AppCompatActivity
import io.github.xiaobaicz.common.provider.ContextProvider
import io.github.xiaobaicz.demo.databinding.ActivityMainBinding

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
