package io.github.xiaobaicz.common.recyclerview

import androidx.viewbinding.ViewBinding

fun interface OnBindingCreate<T : ViewBinding> {
    fun onBindingCreate(bind: T)
}

inline fun <reified T : ViewBinding> ViewBinding.tryCast(block: T.() -> Unit) {
    if (this is T) block(this)
}