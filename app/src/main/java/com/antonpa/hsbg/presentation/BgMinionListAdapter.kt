package com.antonpa.hsbg.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.antonpa.hsbg.R
import com.antonpa.hsbg.domain.BgMinionItem

class BgMinionListAdapter : ListAdapter<BgMinionItem, BgMinionItemViewHolder>(
    BgMinionListAdapterCallback()
) {

    var onBgMinionItemClickListener: ((BgMinionItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BgMinionItemViewHolder {
         val layout = when (viewType) {
            VIEW_TYPE_EVEN_COST -> R.layout.item_bg_minion_even_cost
            VIEW_TYPE_ODD_COST -> R.layout.item_bg_minion_odd_cost
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return BgMinionItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BgMinionItemViewHolder, position: Int) {
        val bgMinionItem = getItem(position)
        with(holder) {
            txtName.text = bgMinionItem.name
            txtCost.text = bgMinionItem.cost.toString()
            itemView.setOnClickListener {
                onBgMinionItemClickListener?.invoke(bgMinionItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).cost % 2 == 0) VIEW_TYPE_EVEN_COST else VIEW_TYPE_ODD_COST

    companion object {
        const val VIEW_TYPE_EVEN_COST = 0
        const val VIEW_TYPE_ODD_COST = 1
        const val MAX_POOL_SIZE = 15
    }
}