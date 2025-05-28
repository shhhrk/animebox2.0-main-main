package com.example.animebox.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animebox.domain.model.Character
import com.example.animebox.domain.usecase.GetCharactersUseCase
import com.example.animebox.domain.usecase.ManageCrystalsUseCase

class MainViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val manageCrystalsUseCase: ManageCrystalsUseCase
) : ViewModel() {

    private val _crystals = MutableLiveData<Int>()
    val crystals: LiveData<Int> = _crystals

    private val _selectedCharacter = MutableLiveData<Character>()
    val selectedCharacter: LiveData<Character> = _selectedCharacter

    init {
        updateCrystals()
    }

    fun updateCrystals() {
        _crystals.value = manageCrystalsUseCase.getCrystals()
    }

    fun onAdCompleted() {
        manageCrystalsUseCase.addAdvertisementReward()
        updateCrystals()
    }

    fun tryOpenLootbox(animeType: AnimeType): Boolean {
        if (!manageCrystalsUseCase.tryOpenLootbox()) {
            return false
        }

        val characters = when (animeType) {
            AnimeType.NARUTO -> getCharactersUseCase.getNarutoCharacters()
            AnimeType.KAGUYA -> getCharactersUseCase.getKaguyaCharacters()
            AnimeType.CHAINSAWMAN -> getCharactersUseCase.getChainsawmanCharacters()
            AnimeType.BOCCHI -> getCharactersUseCase.getBocchiCharacters()
            AnimeType.JOJO -> getCharactersUseCase.getJojoCharacters()
            AnimeType.WINDBREAKER -> getCharactersUseCase.getWindbreakerCharacters()
            AnimeType.DUNGEONMESHI -> getCharactersUseCase.getDungeonMeshiCharacters()
        }

        _selectedCharacter.value = getCharactersUseCase.getRandomCharacterFromList(characters)
        updateCrystals()
        return true
    }

    enum class AnimeType {
        NARUTO, KAGUYA, CHAINSAWMAN, BOCCHI, JOJO, WINDBREAKER, DUNGEONMESHI
    }
} 