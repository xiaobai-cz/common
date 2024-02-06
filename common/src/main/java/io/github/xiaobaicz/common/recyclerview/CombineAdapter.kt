package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * 组合适配器便捷方法
 */
fun RecyclerView.combineAdapter(config: Combine.() -> Unit): CombineAdapter {
    val combine = Combine()
    combine.config()
    val adapter = CombineAdapter(combine)
    this.adapter = adapter
    return adapter
}

/**
 * 视图类型 & Binding 关联
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified V : ViewBinding, D : ViewType> Combine.bind(viewType: Int, binding: OnBinding<V, D>) {
    factoryMap[viewType] = BindingFactory.create<V>() as BindingFactory<ViewBinding>
    bindingMap[viewType] = binding as OnBinding<ViewBinding, ViewType>
}

/**
 * 视图类型
 */
interface ViewType {
    val viewType: Int
}

/**
 * Factory&Binding组合
 */
class Combine(
    val factoryMap: HashMap<Int, BindingFactory<ViewBinding>> = HashMap(),
    val bindingMap: HashMap<Int, OnBinding<ViewBinding, ViewType>> = HashMap(),
)

/**
 * 组合适配器，多类型列表
 */
class CombineAdapter(private val combine: Combine) : BindingListAdapter<ViewBinding, ViewType>() {
    // Binding创建回调
    val onBindingCreateMap = HashMap<Class<*>, OnBindingCreate<ViewBinding>>()

    override fun bindingFactory(viewType: Int): BindingFactory<ViewBinding> {
        return combine.factoryMap[viewType] ?: throw NullPointerException("not find factory by viewType: $viewType")
    }

    override fun onCreate(bind: ViewBinding) {
        onBindingCreateMap[bind::class.java]?.onCreate(bind)
    }

    override fun onBind(bind: ViewBinding, position: Int) {
        val any = data[position]
        val binding = combine.bindingMap[any.viewType] ?: throw NullPointerException("not find binding by viewType: ${any.viewType}")
        binding.onBind(bind, any, position)
    }

    override fun getItemViewType(position: Int): Int = data[position].viewType

    inline fun <reified V : ViewBinding> doOnBindingCreate(onBindingCreate: OnBindingCreate<V>): CombineAdapter {
        @Suppress("UNCHECKED_CAST")
        onBindingCreateMap[V::class.java] = onBindingCreate as OnBindingCreate<ViewBinding>
        return this
    }
}