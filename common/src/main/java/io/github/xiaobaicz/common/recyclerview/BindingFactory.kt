@file:JvmName("BindingFactory")
package io.github.xiaobaicz.common.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup

typealias BindingFactory<T> = (LayoutInflater, ViewGroup, Boolean) -> T