package com.antonpa.hsbg.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonpa.hsbg.R
import com.antonpa.hsbg.domain.BgMinionItem
import java.lang.RuntimeException

class BgMinionListAdapter : RecyclerView.Adapter<BgMinionListAdapter.BgMinionItemViewHolder>() {

    var bgMinionList = listOf<BgMinionItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onBgMinionItemClickListener: ((BgMinionItem) -> Unit)? = null
    var onBgMinionItemLongClickListener: ((BgMinionItem) -> Unit)? = null

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
        val bgMinionItem = bgMinionList[position]
        holder.txtName.text = bgMinionItem.name
        holder.txtCost.text = bgMinionItem.cost.toString()
        holder.itemView.setOnClickListener {
            onBgMinionItemClickListener?.invoke(bgMinionItem)
        }
        holder.itemView.setOnLongClickListener {
            onBgMinionItemLongClickListener?.invoke(bgMinionItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (bgMinionList[position].cost % 2 == 0) VIEW_TYPE_EVEN_COST else VIEW_TYPE_ODD_COST


    override fun getItemCount(): Int = bgMinionList.size


    class BgMinionItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txt_name)
        val txtCost: TextView = view.findViewById(R.id.txt_cost)
    }

    companion object {
        const val VIEW_TYPE_EVEN_COST = 0
        const val VIEW_TYPE_ODD_COST = 1
        const val MAX_POOL_SIZE = 15
    }
}