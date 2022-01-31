package com.antonpa.hsbg.domain

interface BgMinionRepository {

    fun getBgMinionList(): List<BgMinionItem>

    fun getBgMinion(bgMinionId: Int): BgMinionItem

    fun addBgMinion(bgMinionItem: BgMinionItem)

    fun deleteBgMinion(bgMinionItem: BgMinionItem)
}