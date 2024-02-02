package io.github.xiaobaicz.common.lang

import android.os.Looper
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private val mainScope = MainScope()

fun runOnMainThread(block: Runnable) {
    if (isMainThread) {
        block.run()
        return
    }
    mainScope.launch {
        block.run()
    }
}

val isMainThread: Boolean get() = Thread.currentThread() == Looper.getMainLooper().thread