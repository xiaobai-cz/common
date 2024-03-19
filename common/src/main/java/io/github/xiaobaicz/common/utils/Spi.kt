package io.github.xiaobaicz.common.utils

import java.util.ServiceLoader

inline fun <reified T : Any> loadSpi(): List<T> {
    val spiClass = T::class.java
    return try {
        ServiceLoader.load(spiClass, spiClass.classLoader).toList()
    } catch (t: Throwable) {
        emptyList()
    }
}