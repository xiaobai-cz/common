package io.github.xiaobaicz.common.log

import io.github.xiaobaicz.common.utils.loadSpi

private val logList by lazy { loadSpi<Log>() }

fun log(obj: Any?) {
    logList.forEach { it.log(obj) }
}

fun log(format: String, vararg args: Any?) {
    logList.forEach { it.log(format, *args) }
}

fun log(t: Throwable) {
    logList.forEach { it.log(t) }
}

fun log(t: Throwable, format: String, vararg args: Any?) {
    logList.forEach { it.log(t, format, *args) }
}