package com.sultanayubi.customcalendarview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sultanayubi.customcalendarview.SafeClicking.setSafeOnClickListener
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<T, V : ViewDataBinding>(
    @LayoutRes var layout: Int
) : RecyclerView.Adapter<BaseViewHolder<V>>() {

    // Make items accessible via getItems
    private var items: ArrayList<T> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    // Get items list method
    fun getItems(): List<T> {
        return items
    }

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    fun clearList() {
        this.items.clear()
        notifyDataSetChanged()
    }


    fun getList() = items


    open fun onClick(item: T, index: Int) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = BaseViewHolder(
        DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            layout, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {

        onItemInflated(items[position], position, holder.binding)

        // Set safe click listener
        holder.binding.root.setSafeOnClickListener {
            onClick(items[position], position) // Pass item and position
        }

        holder.binding.executePendingBindings()

    }

    abstract fun onItemInflated(item: T, position: Int, binding: V)
}