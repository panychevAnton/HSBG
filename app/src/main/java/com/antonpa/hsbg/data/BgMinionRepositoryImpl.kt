package com.antonpa.hsbg.data

import com.antonpa.hsbg.domain.BgMinionItem
import com.antonpa.hsbg.domain.BgMinionRepository
import java.lang.RuntimeException

object BgMinionRepositoryImpl: BgMinionRepository {

    private val bgMinionList = mutableListOf<BgMinionItem>()

    private var autoIncrementId = 0

    override fun getBgMinionList(): List<BgMinionItem> {
        return bgMinionList.toList()
    }

    override fun getBgMinion(bgMinionId: Int): BgMinionItem {
        return bgMinionList.find {
            it.id == bgMinionId
        } ?: throw RuntimeException("Element with id $bgMinionId not found")
    }

    override fun addBgMinion(bgMinionItem: BgMinionItem) {
        bgMinionItem.id = autoIncrementId++
        bgMinionList.add(bgMinionItem)
    }

    override fun deleteBgMinion(bgMinionItem: BgMinionItem) {
        bgMinionList.remove(bgMinionItem)
    }
}