package com.antonpa.hsbg.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonpa.hsbg.data.BgMinionRepositoryImpl
import com.antonpa.hsbg.domain.AddBgMinionUseCase
import com.antonpa.hsbg.domain.BgMinionItem
import com.antonpa.hsbg.domain.GetBgMinionUseCase
import java.lang.Exception

class BgMinionItemViewModel : ViewModel() {

    private val repository = BgMinionRepositoryImpl

    private val _bgMinionItemLiveData = MutableLiveData<BgMinionItem>()
    val bgMinionItemLiveData: LiveData<BgMinionItem>
        get() = _bgMinionItemLiveData

    private val _isCanBeClosedLiveData = MutableLiveData<Unit>()
    val isCanBeClosedLiveData: LiveData<Unit>
        get() = _isCanBeClosedLiveData

    private val _isCorrectNameLiveData = MutableLiveData<Boolean>()
    val isCorrectNameLiveData: LiveData<Boolean>
        get() = _isCorrectNameLiveData

    private val _isCorrectCostLiveData = MutableLiveData<Boolean>()
    val isCorrectCostLiveData: LiveData<Boolean>
        get() = _isCorrectCostLiveData

    private val getBgMinionItemUseCase = GetBgMinionUseCase(repository)
    private val addBgMinionUseCase = AddBgMinionUseCase(repository)

    fun getBgMinionItem(bgMinionItemId: Int) {
        val item = getBgMinionItemUseCase.getBgMinion(bgMinionItemId)
        _bgMinionItemLiveData.value = item
    }

    fun addBgMinionItem(inputName: String?, inputCost: String?) {
        val name = parseName(inputName)
        val cost = parseCost(inputCost)
        val isValid = isInputDataValid(name, cost)
        if (isValid) {
            val bgMinionItem = BgMinionItem(name = name, cost = cost, imageUrl = "")
            addBgMinionUseCase.addBgMinion(bgMinionItem)
            _isCanBeClosedLiveData.value = Unit
        }
    }

    fun showBgMinionItem() {
        _isCanBeClosedLiveData.value = Unit
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCost(cost: String?): Int {
        return try {
            cost?.trim()?.toInt() ?: 0
        } catch (e: Exception){
            0
        }
    }

    private fun isInputDataValid(name: String, cost: Int): Boolean{
        var result = true
        if (name.isBlank()){
            _isCorrectNameLiveData.value = false
            result = false
        }
        if (cost !in 1..6){
            _isCorrectCostLiveData.value = false
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _isCorrectNameLiveData.value = true
    }

    fun resetErrorInputCost() {
        _isCorrectCostLiveData.value = true
    }
}