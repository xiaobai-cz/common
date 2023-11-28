@file:JvmName("BindingFactory")
package vip.oicp.xiaobaicz.lib.common.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup

typealias BindingFactory<T> = (LayoutInflater, ViewGroup, Boolean) -> T