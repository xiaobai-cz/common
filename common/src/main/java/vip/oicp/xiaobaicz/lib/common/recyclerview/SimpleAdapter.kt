@file:JvmName("RecyclerViewX")

package vip.oicp.xiaobaicz.lib.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> RecyclerView.simpleAdapter(factory: BindingFactory<T>, onBindBinding: (T, Any, Int) -> Unit): SimpleAdapter<T> {
    val adapter = SimpleAdapter(factory, onBindBinding)
    this.adapter = adapter
    return adapter
}

class SimpleAdapter<T : ViewBinding>(
    private val factory: BindingFactory<T>,
    private val onBindBinding: (T, Any, Int) -> Unit,
) : BindingListAdapter<T>() {
    var onBindingCreate: OnBindingCreate<T>? = null

    override fun bindingFactory(viewType: Int): BindingFactory<T> {
        return factory
    }

    override fun onBindingCreate(bind: T) {
        onBindingCreate?.onBindingCreate(bind)
    }

    override fun onBindBinding(bind: T, position: Int) {
        onBindBinding(bind, data[position], position)
    }
}