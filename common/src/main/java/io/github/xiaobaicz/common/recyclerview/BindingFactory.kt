package io.github.xiaobaicz.common.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method

class BindingFactory<V : ViewBinding>(val method: Method) {

    fun create(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): V {
        @Suppress("UNCHECKED_CAST")
        return method.invoke(null, inflater, parent, attachToParent) as V
    }

}

inline fun <reified V : ViewBinding> createBindingFactory(): BindingFactory<V> {
    val clazz = V::class.java
    val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    return BindingFactory(method)
}