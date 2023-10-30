@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.utils

import android.os.SystemClock
import android.view.Choreographer
import android.view.Choreographer.FrameCallback

/**
 * 帧监听工具
 */
object FrameUtils {

    /**
     * 帧率监听
     */
    fun interface FrameRateListener {
        /**
         * 帧率回调
         */
        fun frameRate(rate: Int)
    }

    // 垂直同步信号工具
    @JvmStatic
    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    /**
     * 帧率监听
     */
    @JvmStatic
    var frameRateListener: FrameRateListener? = null

    /**
     * 帧监听
     */
    @JvmStatic
    var frameCallback: FrameCallback? = null

    /**
     * 启动/停止监听
     */
    @JvmStatic
    var running: Boolean = false
        set(value) {
            if (field == value) return
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