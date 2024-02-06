package io.github.xiaobaicz.demo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.xiaobaicz.common.app.AppCompatActivity
import io.github.xiaobaicz.common.provider.ContextProvider
import io.github.xiaobaicz.common.provider.await
import io.github.xiaobaicz.common.recyclerview.ViewType
import io.github.xiaobaicz.common.recyclerview.bind
import io.github.xiaobaicz.common.recyclerview.combineAdapter
import io.github.xiaobaicz.demo.bean.Img
import io.github.xiaobaicz.demo.bean.Msg
import io.github.xiaobaicz.demo.bean.VIEW_TYPE_IMG
import io.github.xiaobaicz.demo.bean.VIEW_TYPE_MSG
import io.github.xiaobaicz.demo.databinding.ActivityMainBinding
import io.github.xiaobaicz.demo.databinding.ItemNewsImgBinding
import io.github.xiaobaicz.demo.databinding.ItemNewsMsgBinding
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

        bind.news.layoutManager = LinearLayoutManager(this)

        val adapter = bind.news.combineAdapter {
            bind<ItemNewsMsgBinding, Msg>(VIEW_TYPE_MSG) { v, d, p ->
                v.title.text = d.title
                v.msg.text = d.msg
            }
            bind<ItemNewsImgBinding, Img>(VIEW_TYPE_IMG) { v, d, p ->
                v.title.text = d.title
                v.img.setBackgroundColor(d.color.toInt())
            }
        }.doOnBindingCreate<ItemNewsMsgBinding> {
            println(it)
        }.doOnBindingCreate<ItemNewsImgBinding> {
            println(it)
        }

        adapter.data = ArrayList<ViewType>().apply {
            val cs = arrayOf(0xffff0000, 0xff00ff00, 0xff0000ff)
            repeat(1000) {
                if (it and 1 == 0) {
                    add(Msg("震惊${it}号", "震惊震惊震惊震惊震惊震惊震惊震"))
                } else {
                    add(Img("震惊${it}号", cs[it % cs.size]))
                }
            }
        }
    }

}
