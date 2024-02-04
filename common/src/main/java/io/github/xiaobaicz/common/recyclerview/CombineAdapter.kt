package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface ViewType {
    val viewType: Int
}

class Combine(
    val factoryMap: HashMap<Int, BindingFactory<ViewBinding>> = HashMap(),
    val bindingMap: HashMap<Int, OnBindBinding<ViewBinding, ViewType>> = HashMap(),
)

class CombineAdapter(private val combine: Combine) : BindingListAdapter<ViewBinding, ViewType>() {
    var onBindingCreate: OnBindingCreate<ViewBinding>? = null

    override fun bindingFactory(viewType: Int): BindingFactory<ViewBinding> {
        return combine.factoryMap[viewType] ?: throw NullPointerException("not find factory by viewType: $viewType")
    }

    override fun onBindingCreate(bind: ViewBinding) {
        onBindingCreate?.onBindingCreate(bind)
    }

    override fun onBindBinding(bind: ViewBinding, position: Int) {
        val any = data[position]
        val binding = combine.bindingMap[any.viewType] ?: throw NullPointerException("not find binding by viewType: ${any.viewType}")
        binding.bind(bind, any, position)
    }

    override fun getItemViewType(position: Int): Int = data[position].viewType
}

@Suppress("UNCHECKED_CAST")
inline fun <reified V : ViewBinding, D : ViewType> Combine.bind(viewType: Int, binding: OnBindBinding<V, D>) {
    factoryMap[viewType] = createBindingFactory<V>() as BindingFactory<ViewBinding>
    bindingMap[viewType] = binding as OnBindBinding<ViewBinding, ViewType>
}

fun RecyclerView.combineAdapter(config: Combine.() -> Unit): CombineAdapter {
    val combine = Combine()
    combine.config()
    val adapter = CombineAdapter(combine)
    this.adapter = adapter
    return adapter
}