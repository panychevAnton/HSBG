package com.antonpa.hsbg.domain

class GetBgMinionUseCase(private val bgMinionRepository: BgMinionRepository) {

    fun getBgMinion(bgMinionId: Int): BgMinionItem {
        return bgMinionRepository.getBgMinion(bgMinionId)
    }
}