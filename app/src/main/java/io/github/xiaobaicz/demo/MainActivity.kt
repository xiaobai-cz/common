package io.github.xiaobaicz.demo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.xiaobaicz.common.app.AppCompatActivity
import io.github.xiaobaicz.common.provider.ContextProvider
import io.github.xiaobaicz.common.provider.await
import io.github.xiaobaicz.common.recyclerview.CombineAdapter
import io.github.xiaobaicz.common.recyclerview.bind
import io.github.xiaobaicz.common.recyclerview.combineAdapter
import io.github.xiaobaicz.demo.bean.Img
import io.github.xiaobaicz.demo.bean.Msg
import io.github.xiaobaicz.demo.constant.VT_IMG
import io.github.xiaobaicz.demo.constant.VT_MSG
import io.github.xiaobaicz.demo.databinding.ActivityMainBinding
import io.github.xiaobaicz.demo.databinding.ItemNewsImgBinding
import io.github.xiaobaicz.demo.databinding.ItemNewsMsgBinding
import io.github.xiaobaicz.demo.test.newsData
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter by lazy { initNews() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        lifecycleScope.launch {
            println(ContextProvider.applicationContext.await())
            println(ContextProvider.visibleActivity.await())
            adapter.data = newsData()
        }
    }

    private fun initNews(): CombineAdapter {
        bind.news.layoutManager = LinearLayoutManager(this)
        return bind.news.combineAdapter {
            bind<ItemNewsMsgBinding, Msg>(VT_MSG) { v, d, p ->
                v.title.text = d.title
                v.msg.text = d.msg
            }
            bind<ItemNewsImgBinding, Img>(VT_IMG) { v, d, p ->
                v.title.text = d.title
                v.img.setBackgroundColor(d.color.toInt())
            }
        }.doOnBindingCreate<ItemNewsMsgBinding> {
            // 创建ItemNewsMsgBinding时回调
            println(it)
        }.doOnBindingCreate<ItemNewsImgBinding> {
            // 创建ItemNewsImgBinding时回调
            println(it)
        }
    }

}
