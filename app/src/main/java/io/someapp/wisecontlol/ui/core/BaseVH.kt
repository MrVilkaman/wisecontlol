package io.someapp.wisecontlol.ui.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseVH<Type>(view: View) : RecyclerView.ViewHolder(view) {

    @Suppress("UNCHECKED_CAST")
    protected val item: Type
        get() = this.itemView.tag as Type

    fun setListeners(
        view: View, onClick: ItemListener<Type>?,
        onLongClick: ItemListener<Type>?
    ) {
        view.setOnClickListener {
            onClick?.click(item)
        }

        view.setOnLongClickListener { view1 ->
            if (onLongClick != null && item != null) {
                onLongClick.click(item)
                return@setOnLongClickListener true
            }
            false
        }
    }

    abstract fun bind(item: Type, position: Int, payloads: Set<String>)
}

interface ItemListener<T> {
    fun click(value: T)
}