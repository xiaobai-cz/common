@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.log

import vip.oicp.xiaobaicz.lib.common.utils.tryCatch

fun println4List(vararg any: Any?) {
    println(StringBuilder().apply {
        append("[")
        append(tryCatch("undefined") {
            Thread.currentThread().stackTrace[6].methodName
        }.result)
        any.forEach {
            append("  ")
            append(it)
        }
        append("]")
    })
}