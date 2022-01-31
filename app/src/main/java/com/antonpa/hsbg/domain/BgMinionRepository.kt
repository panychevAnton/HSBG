package com.antonpa.hsbg.domain

import androidx.lifecycle.LiveData

interface BgMinionRepository {

    fun getBgMinionList(): LiveData<List<BgMinionItem>>

    fun getBgMinion(bgMinionId: Int): BgMinionItem

    fun addBgMinion(bgMinionItem: BgMinionItem)

    fun deleteBgMinion(bgMinionItem: BgMinionItem)
}