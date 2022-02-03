package com.antonpa.hsbg.presentation

import androidx.recyclerview.widget.DiffUtil
import com.antonpa.hsbg.domain.BgMinionItem

class BgMinionListAdapterCallback : DiffUtil.ItemCallback<BgMinionItem>() {
    override fun areItemsTheSame(oldItem: BgMinionItem, newItem: BgMinionItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BgMinionItem, newItem: BgMinionItem): Boolean {
        return oldItem == newItem
    }
}