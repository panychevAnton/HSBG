package com.antonpa.hsbg.domain

interface BgMinionRepository {

    fun getBgMinionList(): List<BgMinionItem>

    fun getBgMinion(): BgMinionItem
}