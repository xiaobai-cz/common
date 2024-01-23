package vip.oicp.xiaobaicz.lib.common.utils

class Result<T>(
    val result: T,
    private val t: Throwable? = null,
) {
    fun doOnError(func: (Throwable)->Unit) {
        func(t ?: return)
    }
}

/**
 * 仅捕捉异常
 */
fun tryCatch(func: () -> Unit): Result<Unit> = try {
    func()
    Result(Unit)
} catch (t: Throwable) {
    Result(Unit, t)
}

/**
 * 仅捕捉异常
 */
fun <T> tryCatch(default: T, func: () -> T): Result<T> = try {
    Result(func())
} catch (t: Throwable) {
    Result(default, t)
}
