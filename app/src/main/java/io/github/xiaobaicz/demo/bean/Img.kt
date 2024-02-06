package io.github.xiaobaicz.demo.bean

import io.github.xiaobaicz.common.recyclerview.ViewType

class Img(
    val title: String,
    val color: Long,
) : ViewType {
    override val viewType: Int get() = VIEW_TYPE_IMG
}

const val VIEW_TYPE_IMG = 2