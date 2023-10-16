@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.log

import vip.oicp.xiaobaicz.lib.common.utils.catchOnly

fun println4List(vararg any: Any?) {
    println(StringBuilder().apply {
        append("[")
        append(catchOnly("undefined") { Thread.currentThread().stackTrace[6].methodName })
        any.forEach {
            append("  ")
            append(it)
        }
        append("]")
    })
}