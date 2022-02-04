package com.antonpa.hsbg.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonpa.hsbg.data.BgMinionRepositoryImpl
import com.antonpa.hsbg.domain.AddBgMinionUseCase
import com.antonpa.hsbg.domain.BgMinionItem
import com.antonpa.hsbg.domain.DeleteBgMinionUseCase
import com.antonpa.hsbg.domain.GetBgMinionListUseCase

class MainViewModel: ViewModel() {

    private val repository = BgMinionRepositoryImpl

    private val getBgMinionListUseCase = GetBgMinionListUseCase(repository)
    private val deleteBgMinionUseCase = DeleteBgMinionUseCase(repository)

    val bgMinionList = getBgMinionListUseCase.getBgMinionList()

    fun deleteBgMinionList(bgMinionItem: BgMinionItem) {
        deleteBgMinionUseCase.deleteBgMinion(bgMinionItem)
    }
}