package com.example.animebox.data.repository

import com.example.animebox.R
import com.example.animebox.domain.model.Character

class CharacterRepository {
    private var userCollection: MutableSet<String>? = null
    private lateinit var prefs: android.content.SharedPreferences

    fun init(context: android.content.Context) {
        prefs = context.getSharedPreferences("UserCollectionPrefs", android.content.Context.MODE_PRIVATE)
        userCollection = prefs.getStringSet("COLLECTION", mutableSetOf())?.toMutableSet()
    }

    fun addCharacterToCollection(character: Character) {
        val current = prefs.getStringSet("COLLECTION", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        current.add(character.name)
        prefs.edit().putStringSet("COLLECTION", current).apply()
    }

    fun getUserCollection(): Set<String> {
        return prefs.getStringSet("COLLECTION", mutableSetOf()) ?: emptySet()
    }

    fun getNarutoCharacters() = listOf(
        Character("Хината", "Обычная", R.drawable.hinata),
        Character("Сакура", "Редкая", R.drawable.sakura),
        Character("Кушина", "Легендарная", R.drawable.kushina)
    )

    fun getKaguyaCharacters() = listOf(
        Character("Кагуя", "Обычная", R.drawable.kaguya),
        Character("Чика", "Редкая", R.drawable.chika),
        Character("Ай", "Легендарная", R.drawable.ai)
    )

    fun getChainsawmanCharacters() = listOf(
        Character("Химено", "Обычная", R.drawable.himeno),
        Character("Пауэр", "Редкая", R.drawable.power),
        Character("Макима", "Легендарная", R.drawable.makima)
    )

    fun getBocchiCharacters() = listOf(
        Character("Кита", "Обычная", R.drawable.kita),
        Character("Хитори", "Редкая", R.drawable.hitori),
        Character("Кикури", "Легендарная", R.drawable.kikuri)
    )

    fun getJojoCharacters() = listOf(
        Character("Джотаро", "Обычная", R.drawable.jotaro),
        Character("Джоске", "Редкая", R.drawable.joske),
        Character("Спидвагон", "Легендарная", R.drawable.speedwagon)
    )

    fun getWindbreakerCharacters() = listOf(
        Character("Хаято", "Обычная", R.drawable.hayato),
        Character("Тогаме", "Редкая", R.drawable.togame),
        Character("Кирию", "Легендарная", R.drawable.kiry)
    )

    fun getDungeonMeshiCharacters() = listOf(
        Character("Фалин", "Обычная", R.drawable.falin),
        Character("Инутаде", "Редкая", R.drawable.inutade),
        Character("Марсиль", "Легендарная", R.drawable.marcille)
    )

    fun getAllCharacters(): List<Character> {
        return getNarutoCharacters() + getKaguyaCharacters() + getChainsawmanCharacters() +
               getBocchiCharacters() + getJojoCharacters() + getWindbreakerCharacters() +
               getDungeonMeshiCharacters()
    }

    fun clearUserCollection() {
        prefs.edit().remove("COLLECTION").apply()
    }
} 