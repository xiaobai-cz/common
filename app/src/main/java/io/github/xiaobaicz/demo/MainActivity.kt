package io.github.xiaobaicz.demo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import io.github.xiaobaicz.common.app.AppCompatActivity
import io.github.xiaobaicz.common.provider.ContextProvider
import io.github.xiaobaicz.common.provider.await
import io.github.xiaobaicz.demo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        lifecycleScope.launch {
            println(ContextProvider.applicationContext.await())
            println(ContextProvider.visibleActivity.await())
        }
    }

}
