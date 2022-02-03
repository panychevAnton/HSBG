package com.antonpa.hsbg.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonpa.hsbg.R

class BgMinionItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txtName: TextView = view.findViewById(R.id.txt_name)
    val txtCost: TextView = view.findViewById(R.id.txt_cost)
}