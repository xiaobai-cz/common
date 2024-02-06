package io.github.xiaobaicz.demo.bean

import io.github.xiaobaicz.common.recyclerview.ViewType
import io.github.xiaobaicz.demo.constant.VT_MSG

class Msg(
    val title: String,
    val msg: String,
) : ViewType {
    override val viewType: Int get() = VT_MSG
}
