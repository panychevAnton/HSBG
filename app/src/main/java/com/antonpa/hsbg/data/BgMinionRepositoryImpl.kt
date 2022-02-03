package com.antonpa.hsbg.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.antonpa.hsbg.domain.BgMinionItem
import com.antonpa.hsbg.domain.BgMinionRepository
import java.lang.RuntimeException

object BgMinionRepositoryImpl: BgMinionRepository {

    private val bgMinionList = mutableListOf<BgMinionItem>()

    private val bgMinionListLD = MutableLiveData<List<BgMinionItem>>()

    private var autoIncrementId = 0

    init {
        for (i in 0 .. 10) {
            val item = BgMinionItem("Name $i", "Url $i", i)
            addBgMinion(item)
        }
    }

    override fun getBgMinionList(): LiveData<List<BgMinionItem>> {
        return bgMinionListLD
    }

    override fun getBgMinion(bgMinionId: Int): BgMinionItem {
        return bgMinionList.find {
            it.id == bgMinionId
        } ?: throw RuntimeException("Element with id $bgMinionId not found")
    }

    override fun addBgMinion(bgMinionItem: BgMinionItem) {
        bgMinionItem.id = autoIncrementId++
        bgMinionList.add(bgMinionItem)
        updateLiveDate()
    }

    override fun deleteBgMinion(bgMinionItem: BgMinionItem) {
        bgMinionList.remove(bgMinionItem)
        updateLiveDate()
    }

    private fun updateLiveDate() {
        bgMinionListLD.value = bgMinionList.toList()
    }
}