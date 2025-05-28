package com.example.animebox.domain.usecase

import com.example.animebox.data.repository.CharacterRepository
import com.example.animebox.domain.model.Character
import kotlin.random.Random

class GetCharactersUseCase(private val repository: CharacterRepository) {
    
    fun getRandomCharacterFromList(characters: List<Character>): Character {
        val random = Random.nextInt(100)
        return when {
            random < 70 -> characters[0] // Обычный (70%)
            random < 95 -> characters[1] // Редкий (25%)
            else -> characters[2]        // Легендарный (5%)
        }
    }

    fun getNarutoCharacters() = repository.getNarutoCharacters()
    fun getKaguyaCharacters() = repository.getKaguyaCharacters()
    fun getChainsawmanCharacters() = repository.getChainsawmanCharacters()
    fun getBocchiCharacters() = repository.getBocchiCharacters()
    fun getJojoCharacters() = repository.getJojoCharacters()
    fun getWindbreakerCharacters() = repository.getWindbreakerCharacters()
    fun getDungeonMeshiCharacters() = repository.getDungeonMeshiCharacters()
} 