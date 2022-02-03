package com.antonpa.hsbg.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.antonpa.hsbg.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var bgMinionListAdapter: BgMinionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapterToRv()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.bgMinionList.observe(this){
            bgMinionListAdapter.submitList(it)
        }
    }

    private fun setAdapterToRv() {
        val rvBgMinionList = findViewById<RecyclerView>(R.id.rv_bg_minion_list)
        bgMinionListAdapter = BgMinionListAdapter()
        with(BgMinionListAdapter) {
            rvBgMinionList.adapter = bgMinionListAdapter
            rvBgMinionList.recycledViewPool.setMaxRecycledViews(VIEW_TYPE_EVEN_COST, MAX_POOL_SIZE)
            rvBgMinionList.recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ODD_COST, MAX_POOL_SIZE)
        }
        setBgMinionAdapterItemClickListener()
        setBgMinionAdapterItemLongClickListener()
        setBgMinionAdapterSwipeCallback(rvBgMinionList)
    }

    private fun setBgMinionAdapterItemLongClickListener() {
        bgMinionListAdapter.onBgMinionItemLongClickListener = {
            it.cost++
        }
    }

    private fun setBgMinionAdapterSwipeCallback(rvBgMinionList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = bgMinionListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteBgMinionList(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvBgMinionList)
    }

    private fun setBgMinionAdapterItemClickListener() {
        bgMinionListAdapter.onBgMinionItemClickListener = {
            Log.d("MainActivity", it.toString())
        }
    }
}