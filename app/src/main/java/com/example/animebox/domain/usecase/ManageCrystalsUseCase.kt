package com.example.animebox.domain.usecase

import com.example.animebox.data.repository.CrystalsRepository

class ManageCrystalsUseCase(private val repository: CrystalsRepository) {
    
    fun getCrystals() = repository.getCrystals()
    
    fun addAdvertisementReward() {
        repository.addCrystals(200)
    }
    
    fun tryOpenLootbox(): Boolean {
        return repository.removeCrystals(50)
    }
} 