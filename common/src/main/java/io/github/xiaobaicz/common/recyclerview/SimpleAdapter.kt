package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <reified V : ViewBinding, D : Any> RecyclerView.simpleAdapter(onBindBinding: OnBindBinding<V, D>): SimpleAdapter<V, D> {
    val adapter = SimpleAdapter(createBindingFactory(), onBindBinding)
    this.adapter = adapter
    return adapter
}

class SimpleAdapter<T : ViewBinding, D : Any>(
    private val factory: BindingFactory<T>,
    private val onBindBinding: OnBindBinding<T, D>,
) : BindingListAdapter<T, D>() {
    var onBindingCreate: OnBindingCreate<T>? = null

    override fun bindingFactory(viewType: Int): BindingFactory<T> {
        return factory
    }

    override fun onBindingCreate(bind: T) {
        onBindingCreate?.onBindingCreate(bind)
    }

    override fun onBindBinding(bind: T, position: Int) {
        onBindBinding.bind(bind, data[position], position)
    }
}