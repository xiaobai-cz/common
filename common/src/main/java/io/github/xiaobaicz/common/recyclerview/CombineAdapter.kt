package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface ViewType {
    val viewType: Int
}

class Combine(
    val factoryMap: HashMap<Int, BindingFactory<ViewBinding>> = HashMap(),
    val bindingMap: HashMap<Int, OnBindBinding<ViewBinding>> = HashMap(),
)

class CombineAdapter(private val combine: Combine) : BindingListAdapter<ViewBinding>() {
    var onBindingCreate: OnBindingCreate<ViewBinding>? = null

    override fun bindingFactory(viewType: Int): BindingFactory<ViewBinding> {
        return combine.factoryMap[viewType] ?: throw NullPointerException("not find factory by viewType: $viewType")
    }

    override fun onBindingCreate(bind: ViewBinding) {
        onBindingCreate?.onBindingCreate(bind)
    }

    override fun onBindBinding(bind: ViewBinding, position: Int) {
        val any = data[position]
        if (any !is ViewType) throw IllegalArgumentException("data is not impl ViewType by position: $position")
        val binding = combine.bindingMap[any.viewType] ?: throw NullPointerException("not find binding by viewType: ${any.viewType}")
        binding(bind, any, position)
    }

    override fun getItemViewType(position: Int): Int = when (val any = data[position]) {
        is ViewType -> any.viewType
        else -> throw IllegalArgumentException("data is not impl ViewType by position: $position")
    }
}

fun <T : ViewBinding> Combine.bind(viewType: Int, factory: BindingFactory<T>, binding: OnBindBinding<T>) {
    factoryMap[viewType] = factory
    @Suppress("UNCHECKED_CAST")
    bindingMap[viewType] = binding as OnBindBinding<ViewBinding>
}

fun RecyclerView.combineAdapter(config: Combine.() -> Unit): CombineAdapter {
    val combine = Combine()
    combine.config()
    val adapter = CombineAdapter(combine)
    this.adapter = adapter
    return adapter
}