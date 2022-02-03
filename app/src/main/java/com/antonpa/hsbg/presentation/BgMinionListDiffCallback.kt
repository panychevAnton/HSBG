package com.antonpa.hsbg.presentation

import androidx.recyclerview.widget.DiffUtil
import com.antonpa.hsbg.domain.BgMinionItem

class BgMinionListDiffCallback(
    private val oldList: List<BgMinionItem>,
    private val newList: List<BgMinionItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}