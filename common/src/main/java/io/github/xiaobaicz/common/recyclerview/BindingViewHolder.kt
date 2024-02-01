package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BindingViewHolder<T : ViewBinding>(val bind: T) : RecyclerView.ViewHolder(bind.root)