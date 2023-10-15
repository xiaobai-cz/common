package vip.oicp.xiaobaicz.lib.common.utils

/**
 * 仅捕捉异常
 */
fun catchOnly(func: () -> Unit) {
    try {
        func()
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}

/**
 * 仅捕捉异常
 */
fun <T> catchOnly(default: T, func: () -> T): T = try {
    func()
} catch (t: Throwable) {
    t.printStackTrace()
    default
}