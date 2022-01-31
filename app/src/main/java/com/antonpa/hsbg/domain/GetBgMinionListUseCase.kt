package com.antonpa.hsbg.domain

class GetBgMinionListUseCase(private val bgMinionRepository: BgMinionRepository) {

    fun getBgMinionList(): List<BgMinionItem>{
        return bgMinionRepository.getBgMinionList()
    }
}