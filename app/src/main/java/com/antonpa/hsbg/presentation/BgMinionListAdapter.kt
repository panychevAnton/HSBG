package com.antonpa.hsbg.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonpa.hsbg.R
import com.antonpa.hsbg.domain.BgMinionItem

class BgMinionListAdapter : RecyclerView.Adapter<BgMinionListAdapter.BgMinionItemViewHolder>() {

    var bgMinionList = listOf<BgMinionItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BgMinionItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_bg_minion,
            parent,
            false
        )
        return BgMinionItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BgMinionItemViewHolder, position: Int) {
        val bgMinionItem = bgMinionList[position]
        holder.txtName.text = bgMinionItem.name
        holder.txtCost.text = bgMinionItem.cost.toString()
    }

    override fun getItemCount(): Int = bgMinionList.size


    class BgMinionItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName = view.findViewById<TextView>(R.id.txt_name)
        val txtCost = view.findViewById<TextView>(R.id.txt_cost)
    }
}