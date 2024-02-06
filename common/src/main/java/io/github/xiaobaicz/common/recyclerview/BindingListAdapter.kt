package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

/**
 * BindingAdapter基础上集成 数据集 & DiffUtil
 */
abstract class BindingListAdapter<V: ViewBinding, D : Any> : BindingAdapter<V>() {

    private val callback: CallbackWrapper<D> = CallbackWrapper()

    /**
     * 数据集，通过重新赋值触发DiffUtil
     */
    var data: List<D> = emptyList()
        set(data) {
            val newData = if (field == data) data.toList() else data
            callback.oldData = field
            callback.newData = newData
            field = newData
            DiffUtil.calculateDiff(callback)
                .dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = data.size

    /**
     * DiffUtil对比器
     */
    fun setCallback(callback: CompareCallback<D>): BindingListAdapter<V, D> {
        this.callback.callback = callback
        return this
    }

    /**
     * DiffUtil对比器
     */
    interface CompareCallback<D> {
        /**
         * 对象是否一致
         * @return true 一致
         */
        fun areItemsTheSame(oldData: D, newData: D): Boolean

        /**
         * 对象一致的前提下，对象内容是否一致。判断是否触发Payload
         * @return true 内容一致
         */
        fun areContentsTheSame(oldData: D, newData: D): Boolean = true

        /**
         * 获取Payload对象
         * @return Payload对象
         */
        fun getChangePayload(oldData: D, newData: D): D? = null
    }

    // DiffUtil对比器的包装类
    private class CallbackWrapper<D> : DiffUtil.Callback() {
        var callback: CompareCallback<D>? = null
        var oldData: List<D> = emptyList()
        var newData: List<D> = emptyList()
        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return callback?.areItemsTheSame(oldData[oldItemPosition], newData[newItemPosition]) ?: false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return callback?.areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition]) ?: false
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return callback?.getChangePayload(oldData[oldItemPosition], newData[newItemPosition])
        }
    }

}