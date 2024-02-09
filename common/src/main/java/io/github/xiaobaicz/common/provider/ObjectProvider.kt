package io.github.xiaobaicz.common.provider

import io.github.xiaobaicz.common.lang.runOnMainThread
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import kotlin.coroutines.resume

/**
 * 实现了获取对象行为
 * - 对象为空：等待对象不为空时返回
 * - 对象为空：不等待，直接返回null
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
            if (obj != null)
                callback.value(obj)
            else
                awaitList.add(callback)
        }
    }

    /**
     * 即时获取对象，为空情况下不等待，返回null
     * @return 对象，可空
     */
    fun get(): T? {
        return ref?.get()
    }

    /**
     * 设置对象，并唤醒所有等待中的get方法
     * @param obj 对象
     * @param cache 是否缓存，下次get能否获得该值
     */
    fun set(obj: T, cache: Boolean = true) {
        runOnMainThread {
            ref?.clear()
            ref = if (cache) WeakReference(obj) else null
            notifyAll(obj)
        }
    }

    // 通知回调列表，并清空
    private fun notifyAll(obj: T) {
        for (callback in awaitList)
            callback.value(obj)
        awaitList.clear()
    }

}

/**
 * 获取对象，等待到对象非空
 */
suspend fun <T : Any> ObjectProvider<T>.await(): T = suspendCancellableCoroutine {
    get(it::resume)
}
