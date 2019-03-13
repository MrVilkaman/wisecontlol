package io.someapp.wisecontlol.ui.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleBaseAdapter<T, VH : BaseVH<T>> : RecyclerView.Adapter<VH>() {
    var onClick: ItemListener<T>? = null
    var onLongClick: ItemListener<T>? = null

    var items: List<T> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    protected abstract fun getLayoutId(): Int

    val isEmpty: Boolean
        get() = itemCount == 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val context = parent.context
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(getLayoutId(), parent, false)
        val holder = getHolder(view)
        holder.setListeners(view, onClick, onLongClick)
        return holder
    }

    protected abstract fun getHolder(view: View): VH

    fun getItem(pos: Int): T = items[pos]

    override fun onBindViewHolder(holder: VH, position: Int) {
        // пустой потому что есть метод ниже)
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        val item = getItem(position)
        holder.itemView.tag = item
        if (payloads.isEmpty()) {
            holder.bind(item, position, setOf())
        } else {
            @Suppress("UNCHECKED_CAST")
            holder.bind(item, position, payloads[0] as Set<String>)
        }
    }

    override fun getItemCount(): Int = items.size

    companion object {

        private val TAG = "MySimpleBaseAdapter"
    }
}
