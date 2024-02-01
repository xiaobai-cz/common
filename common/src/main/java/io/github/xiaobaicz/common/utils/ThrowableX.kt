package io.github.xiaobaicz.common.utils

class Result<T>(
    val value: T,
    private val t: Throwable? = null,
) {
    fun doOnError(func: (Throwable)->Unit): Result<T> {
        func(t ?: return this)
        return this
    }
}

/**
 * 捕捉异常，并返回默认值
 */
fun <T> tryCatch(default: T, func: () -> T): Result<T> = try {
    Result(func())
} catch (t: Throwable) {
    Result(default, t)
}

/**
 * 捕捉异常
 */
fun tryCatch(func: () -> Unit): Result<Unit> = tryCatch(Unit, func)
