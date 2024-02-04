package io.github.xiaobaicz.common.recyclerview

fun interface OnBindBinding<V, D> {
    fun bind(v: V, d: D, p: Int)
}
