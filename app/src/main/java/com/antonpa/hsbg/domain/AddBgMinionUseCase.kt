package com.antonpa.hsbg.domain

class AddBgMinionUseCase(private val bgMinionRepository: BgMinionRepository) {

    fun addBgMinion(bgMinionItem: BgMinionItem){
        bgMinionRepository.addBgMinion(bgMinionItem)
    }
}