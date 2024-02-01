@file:Suppress("unused")
package io.github.xiaobaicz.common.log

import io.github.xiaobaicz.common.utils.tryCatch

fun println4List(vararg any: Any?) {
    println(StringBuilder().apply {
        append("[")
        append(tryCatch("undefined") {
            Thread.currentThread().stackTrace[6].methodName
        }.value)
        any.forEach {
            append("  ")
            append(it)
        }
        append("]")
    })
}