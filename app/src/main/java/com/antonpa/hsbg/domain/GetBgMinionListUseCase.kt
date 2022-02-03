package com.antonpa.hsbg.domain

import androidx.lifecycle.LiveData

class GetBgMinionListUseCase(private val bgMinionRepository: BgMinionRepository) {

    fun getBgMinionList(): LiveData<List<BgMinionItem>> {
        return bgMinionRepository.getBgMinionList()
    }
}