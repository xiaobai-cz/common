package io.github.xiaobaicz.common.provider

import io.github.xiaobaicz.common.lang.runOnMainThread
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import kotlin.coroutines.resume

/**
 * ### 对象提供者
 * 实现了获取对象行为
 * - 对象为空：等待对象不为空时返回
 * - 对象非空：直接返回
 */
class ObjectProvider<T : Any> {

    /**
     * 对象回调接口
     */
    fun interface Callback<T : Any> {
        /**
         * 返回值
         */
        fun value(obj: T)
    }

    // 对象引用
    private var ref: Reference<T>? = null

    // 回调列表
    private val awaitList = ArrayList<Callback<T>>()

    /**
     * 清空对象
     */
    fun clear() {
        runOnMainThread {
            ref?.clear()
            ref = null
        }
    }

    /**
     * 获取对象
     * - 为空：等待对象非空
     * - 非空：直接返回
     */
    fun get(callback: Callback<T>) {
        runOnMainThread {
            val obj = ref?.get()
            if (obj != null) {
                callback.value(obj)
                return@runOnMainThread
            }
            awaitList.add(callback)
        }
    }

    /**
     * 设置对象
     */
    fun set(obj: T) {
        runOnMainThread {
            ref?.clear()
            ref = WeakReference(obj)
            signal()
        }
    }

    // 通知回调列表，并清空
    private fun signal() {
        val obj = ref?.get() ?: return
        for (callback in awaitList)
            callback.value(obj)
        awaitList.clear()
    }

}

/**
 * 获取对象，等待到对象非空
 */
suspend fun <T : Any> ObjectProvider<T>.await(): T = suspendCancellableCoroutine { c ->
    get { obj ->
        c.resume(obj)
    }
}
