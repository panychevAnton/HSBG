package com.antonpa.hsbg.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.antonpa.hsbg.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BgMinionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapterToRV()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.bgMinionList.observe(this){
            adapter.bgMinionList = it
        }
    }

    private fun setAdapterToRV(){
        val rvBgMinionList = findViewById<RecyclerView>(R.id.rv_bg_minion_list)
        adapter = BgMinionListAdapter()
        rvBgMinionList.adapter = adapter
    }
}