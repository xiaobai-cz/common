package vip.oicp.xiaobaicz.lib.common.recyclerview

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BindingListAdapter<T: ViewBinding> : BindingAdapter<T>() {

    private val callback: CallbackWrapper = CallbackWrapper()

    var data: List<Any> = emptyList()
        set(value) {
            callback.oldData = field
            callback.newData = value
            field = value
            val diff = DiffUtil.calculateDiff(callback)
            diff.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = data.size

    fun setCallback(callback: CompareCallback) {
        this.callback.callback = callback
    }

    interface CompareCallback {
        fun areItemsTheSame(oldData: Any, newData: Any): Boolean
        fun areContentsTheSame(oldData: Any, newData: Any): Boolean = true
        fun getChangePayload(oldData: Any, newData: Any): Any? = null
    }

    private class CallbackWrapper : DiffUtil.Callback() {
        var callback: CompareCallback? = null
        var oldData: List<Any> = emptyList()
        var newData: List<Any> = emptyList()
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