package io.github.xiaobaicz.common.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingAdapter<V : ViewBinding> : RecyclerView.Adapter<BindingViewHolder<V>>(), OnBindingCreate<V> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<V> {
        val bind = bindingFactory(viewType).create(LayoutInflater.from(parent.context), parent, false)
        onBindingCreate(bind)
        return BindingViewHolder(bind)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<V>, position: Int) {
        onBindBinding(holder.bind, position)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<V>, position: Int, payloads: MutableList<Any>) {
        onBindBinding(holder.bind, position, payloads)
    }

    abstract fun bindingFactory(viewType: Int): BindingFactory<V>

    override fun onBindingCreate(bind: V) {}

    abstract fun onBindBinding(bind: V, position: Int)

    open fun onBindBinding(bind: V, position: Int, payloads: MutableList<Any>) {
        onBindBinding(bind, position)
    }
}