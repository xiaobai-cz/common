package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * 简单适配器便捷方法
 */
inline fun <reified V : ViewBinding, D : Any> RecyclerView.simpleAdapter(onBinding: OnBinding<V, D>): SimpleAdapter<V, D> {
    val adapter = SimpleAdapter(BindingFactory.create<V>(), onBinding)
    this.adapter = adapter
    return adapter
}

/**
 * 简单适配器，单一类型列表
 */
class SimpleAdapter<V : ViewBinding, D : Any>(
    private val factory: BindingFactory<V>,
    private val onBinding: OnBinding<V, D>,
) : BindingListAdapter<V, D>() {
    // Binding创建回调
    private var onBindingCreate: OnBindingCreate<V>? = null

    override fun bindingFactory(viewType: Int): BindingFactory<V> {
        return factory
    }

    override fun onCreate(bind: V) {
        onBindingCreate?.onCreate(bind)
    }

    override fun onBind(bind: V, position: Int) {
        onBinding.onBind(bind, data[position], position)
    }

    /**
     * Binding创建回调
     */
    fun doOnBindingCreate(onBindingCreate: OnBindingCreate<V>): SimpleAdapter<V, D> {
        this.onBindingCreate = onBindingCreate
        return this
    }
}