package io.github.xiaobaicz.demo.bean

import io.github.xiaobaicz.common.recyclerview.ViewType

class Msg(
    val title: String,
    val msg: String,
) : ViewType {
    override val viewType: Int get() = VIEW_TYPE_MSG
}

const val VIEW_TYPE_MSG = 1