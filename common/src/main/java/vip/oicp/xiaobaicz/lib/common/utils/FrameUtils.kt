@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.utils

import android.os.SystemClock
import android.view.Choreographer
import android.view.Choreographer.FrameCallback

object FrameUtils {

    fun interface FrameRateListener {
        fun frameRate(rate: Int)
    }

    @JvmStatic
    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    @JvmStatic
    var frameRateListener: FrameRateListener? = null

    @JvmStatic
    var frameCallback: FrameCallback? = null

    @JvmStatic
    var running: Boolean = false
        set(value) {
            if (field && value) return
            field = value
            if (field) {
                choreographer.postFrameCallback(object : FrameCallback {
                    var time: Long = SystemClock.elapsedRealtime()
                    var rate: Int = 1
                    override fun doFrame(frameTimeNanos: Long) {
                        frameCallback?.doFrame(frameTimeNanos)
                        if (SystemClock.elapsedRealtime() - time >= 1000L) {
                            time = SystemClock.elapsedRealtime()
                            frameRateListener?.frameRate(rate)
                            rate = 0
                        }
                        rate++
                        if (field) {
                            choreographer.postFrameCallback(this)
                        }
                    }
                })
            }
        }

}