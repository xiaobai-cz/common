package io.github.xiaobaicz.common.provider

import io.github.xiaobaicz.common.lang.runOnMainThread
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import kotlin.coroutines.resume

class ObjectProvider<T : Any> {

    fun interface Callback<T : Any> {
        fun value(obj: T)
    }

    private var ref: Reference<T>? = null

    private val awaitList = ArrayList<Callback<T>>()

    fun clear() {
        runOnMainThread {
            ref?.clear()
            ref = null
        }
    }

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

    fun set(obj: T) {
        runOnMainThread {
            ref?.clear()
            ref = WeakReference(obj)
            signal()
        }
    }

    private fun signal() {
        val obj = ref?.get() ?: return
        val callbacks = awaitList.toList()
        awaitList.clear()
        for (callback in callbacks) {
            callback.value(obj)
        }
    }

}

suspend fun <T : Any> ObjectProvider<T>.await(): T = suspendCancellableCoroutine { c ->
    get { obj ->
        c.resume(obj)
    }
}
