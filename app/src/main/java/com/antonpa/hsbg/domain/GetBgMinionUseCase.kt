package com.antonpa.hsbg.domain

class GetBgMinionUseCase(private val bgMinionRepository: BgMinionRepository) {

    fun getBgMinion(BgMinionId: Int): BgMinionItem {
        return bgMinionRepository.getBgMinion()
    }
}