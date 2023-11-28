package vip.oicp.xiaobaicz.lib.common.recyclerview

import androidx.viewbinding.ViewBinding

fun interface OnBindingCreate<T : ViewBinding> {
    fun onBindingCreate(bind: T)
}