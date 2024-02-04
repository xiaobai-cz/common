package io.github.xiaobaicz.common.recyclerview

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BindingListAdapter<V: ViewBinding, D : Any> : BindingAdapter<V>() {

    private val callback: CallbackWrapper<D> = CallbackWrapper()

    var data: List<D> = emptyList()
        set(value) {
            callback.oldData = field
            callback.newData = value
            field = value
            val diff = DiffUtil.calculateDiff(callback)
            diff.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = data.size

    fun setCallback(callback: CompareCallback<D>) {
        this.callback.callback = callback
    }

    interface CompareCallback<D> {
        fun areItemsTheSame(oldData: D, newData: D): Boolean
        fun areContentsTheSame(oldData: D, newData: D): Boolean = true
        fun getChangePayload(oldData: D, newData: D): D? = null
    }

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