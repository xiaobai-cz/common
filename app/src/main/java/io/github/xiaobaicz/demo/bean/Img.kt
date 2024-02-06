package io.github.xiaobaicz.demo.bean

import io.github.xiaobaicz.common.recyclerview.ViewType
import io.github.xiaobaicz.demo.constant.VT_IMG

class Img(
    val title: String,
    val color: Long,
) : ViewType {
    override val viewType: Int get() = VT_IMG
}
