package com.untilled.roadcapture.features.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R

abstract class BindingViewHolder<out T: ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {
    val binding: T? = DataBindingUtil.bind(view)
}

abstract class BaseRecyclerViewAdapter<T, VDB : ViewDataBinding>(var items: List<T>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<T, VDB>.BaseViewHolder>() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    var binding: Any? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewAdapter<T, VDB>.BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                getLayoutResId(),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    inner class BaseViewHolder(view: View) : BindingViewHolder<VDB>(view)
}