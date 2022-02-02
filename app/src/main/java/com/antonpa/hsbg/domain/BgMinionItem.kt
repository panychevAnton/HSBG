package com.antonpa.hsbg.domain

data class BgMinionItem(
    val name: String,
    val imageUrl: String,
    var cost: Int,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
