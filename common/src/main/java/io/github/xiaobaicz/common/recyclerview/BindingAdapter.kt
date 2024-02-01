package io.github.xiaobaicz.common.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingAdapter<T : ViewBinding> : RecyclerView.Adapter<BindingViewHolder<T>>(), OnBindingCreate<T> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val bind = bindingFactory(viewType)(LayoutInflater.from(parent.context), parent, false)
        onBindingCreate(bind)
        return BindingViewHolder(bind)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        onBindBinding(holder.bind, position)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int, payloads: MutableList<Any>) {
        onBindBinding(holder.bind, position, payloads)
    }

    abstract fun bindingFactory(viewType: Int): BindingFactory<T>

    override fun onBindingCreate(bind: T) {}

    abstract fun onBindBinding(bind: T, position: Int)

    open fun onBindBinding(bind: T, position: Int, payloads: MutableList<Any>) {
        onBindBinding(bind, position)
    }
}