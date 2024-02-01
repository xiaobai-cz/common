package io.github.xiaobaicz.common.provider

import androidx.annotation.MainThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ObjectProvider<T : Any> {
    private var ref: Reference<T>? = null
    private val lock: ReentrantLock by lazy { ReentrantLock(true) }
    private val cond: Condition by lazy { lock.newCondition() }
    private val scope = MainScope()

    @MainThread
    fun get(block: (T) -> Unit) {
        val obj = ref?.get()
        if (obj != null) {
            block(obj)
            return
        }
        scope.launch {
            awaitGet(block)
        }
    }

    @MainThread
    fun clear() {
        lock.withLock {
            ref?.clear()
            ref = null
        }
    }

    @MainThread
    fun set(obj: T) {
        lock.withLock {
            ref?.clear()
            ref = WeakReference(obj)
            cond.signalAll()
        }
    }

    private suspend fun awaitGet(block: (T) -> Unit) {
        block(withContext(Dispatchers.IO) {
            lock.withLock {
                var obj = ref?.get()
                while (obj == null) {
                    cond.await()
                    obj = ref?.get()
                }
                obj
            }
        })
    }
}