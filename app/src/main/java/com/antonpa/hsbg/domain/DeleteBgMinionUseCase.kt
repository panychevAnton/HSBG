package com.antonpa.hsbg.domain

class DeleteBgMinionUseCase(private val bgMinionRepository: BgMinionRepository) {

    fun deleteBgMinion(bgMinionItem: BgMinionItem){
        bgMinionRepository.deleteBgMinion(bgMinionItem)
    }
}