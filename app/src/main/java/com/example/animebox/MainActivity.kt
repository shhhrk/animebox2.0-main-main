package com.example.animebox

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import kotlin.random.Random
import com.example.animebox.data.repository.CharacterRepository
import com.example.animebox.domain.model.Character
import com.example.animebox.ui.collection.CollectionActivity
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private var crystals = 0
    private lateinit var prefs: android.content.SharedPreferences
    private lateinit var crystalsTextView: TextView
    private lateinit var adImage: ImageView
    private lateinit var btnWatchAd: ImageButton
    private var isAdShowing = false
    private lateinit var adTimer: CountDownTimer
    private lateinit var characterRepository: CharacterRepository

    private val narutoCharacters = listOf(
        Character("Хината", "Обычная", R.drawable.hinata),
        Character("Сакура", "Редкая", R.drawable.sakura),
        Character("Кушина", "Легендарная", R.drawable.kushina)
    )

    private val kaguyaCharacters = listOf(
        Character("Кагуя", "Обычная", R.drawable.kaguya),
        Character("Чика", "Редкая", R.drawable.chika),
        Character("Ай", "Легендарная", R.drawable.ai)
    )
    private val chainsawmanCharacters = listOf(
        Character("Химено", "Обычная", R.drawable.himeno),
        Character("Пауэр", "Редкая", R.drawable.power),
        Character("Макима", "Легендарная", R.drawable.makima)
    )
    private val bocchitherockerCharacters = listOf(
        Character("Кита", "Обычная", R.drawable.kita),
        Character("Хитори", "Редкая", R.drawable.hitori),
        Character("Кикури", "Легендарная", R.drawable.kikuri)
    )
    private val jojoCharacters = listOf(
        Character("Джотаро", "Обычная", R.drawable.jotaro),
        Character("Джоске", "Редкая", R.drawable.joske),
        Character("Спидвагон", "Легендарная", R.drawable.speedwagon)
    )
    private val windbreakerCharacters = listOf(
        Character("Хаято", "Обычная", R.drawable.hayato),
        Character("Тогаме", "Редкая", R.drawable.togame),
        Character("Кирию", "Легендарная", R.drawable.kiry)
    )
    private val dungeonmeshiCharacters = listOf(
        Character("Фалин", "Обычная", R.drawable.falin),
        Character("Инутаде", "Редкая", R.drawable.inutade),
        Character("Марсиль", "Легендарная", R.drawable.marcille)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crystalsTextView = findViewById(R.id.crystalsTextView)
        adImage = findViewById(R.id.adImage)
        adImage.setImageResource(R.drawable.ad_image)
        btnWatchAd = findViewById(R.id.btnWatchAd)

        prefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        crystals = prefs.getInt("CRYSTALS", 0)
        updateCrystalsDisplay()

        characterRepository = CharacterRepository()
        characterRepository.init(this)

        findViewById<Button>(R.id.btnOpenNarutoBox).setOnClickListener { openAnimeBox(narutoCharacters) }
        findViewById<Button>(R.id.btnOpenKaguyaSamaBox).setOnClickListener { openAnimeBox(kaguyaCharacters) }
        findViewById<Button>(R.id.btnOpenChainsawmanBox).setOnClickListener { openAnimeBox(chainsawmanCharacters) }
        findViewById<Button>(R.id.btnOpenBocchitheRockerSamaBox).setOnClickListener { openAnimeBox(bocchitherockerCharacters) }
        findViewById<Button>(R.id.btnOpenJojoBox).setOnClickListener { openAnimeBox(jojoCharacters) }
        findViewById<Button>(R.id.btnOpenWindBreakerBox).setOnClickListener { openAnimeBox(windbreakerCharacters) }
        findViewById<Button>(R.id.btnOpenDungeonMeshiBox).setOnClickListener { openAnimeBox(dungeonmeshiCharacters) }
        findViewById<ImageButton>(R.id.btnOpenCollection).setOnClickListener {
            val intent = android.content.Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.btnOpenSettings).setOnClickListener {
            val intent = android.content.Intent(this, com.example.animebox.ui.settings.SettingsActivity::class.java)
            startActivity(intent)
        }
        btnWatchAd.setOnClickListener { showAd() }
        }

    private fun openAnimeBox(characters: List<Character>) {
        if (crystals >= 50) {
            crystals -= 50
            saveCrystals()

            val random = Random.nextInt(100)
            val obtainedCharacter = when {
                random < 70 -> characters[0]
                random < 95 -> characters[1]
                else -> characters[2]
            }

            showCharacterDialog(obtainedCharacter)
        } else {
            Toast.makeText(this, "Недостаточно кристаллов!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCharacterDialog(character: Character) {
        val dialogView = layoutInflater.inflate(R.layout.character_dialog, null)
        dialogView.findViewById<TextView>(R.id.characterName).text = character.name
        dialogView.findViewById<TextView>(R.id.characterRarity).text = "Редкость: ${character.rarity}"
        dialogView.findViewById<ImageView>(R.id.characterImage).setImageResource(character.imageRes)

        AlertDialog.Builder(ContextThemeWrapper(this, R.style.PinkDialog))
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                characterRepository.addCharacterToCollection(character)
                dialog.dismiss()
            }
            .show()
    }

    public fun showAd() {
        if (!isAdShowing) {
            isAdShowing = true
            btnWatchAd.isEnabled = false

            val dialogView = layoutInflater.inflate(R.layout.ad_dialog, null)
            val adImageView = dialogView.findViewById<ImageView>(R.id.adImageView)
            val timerTextView = dialogView.findViewById<TextView>(R.id.timerTextView)
            adImageView.setImageResource(R.drawable.ad_image)

            val dialog = AlertDialog.Builder(ContextThemeWrapper(this, R.style.PinkDialog))
                .setView(dialogView)
                .setCancelable(false)
                .create()

            dialog.show()

            adTimer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTextView.text = "Осталось: ${millisUntilFinished / 1000} сек"
                }

                override fun onFinish() {
                    crystals += 3000
                    saveCrystals()
                    btnWatchAd.isEnabled = true
                    isAdShowing = false
                    dialog.dismiss()
                    updateCrystalsDisplay()
                    Toast.makeText(this@MainActivity, "+200 кристаллов\uD83D\uDC8E!", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }

    private fun saveCrystals() {
        prefs.edit().putInt("CRYSTALS", crystals).apply()
        updateCrystalsDisplay()
    }

    private fun updateCrystalsDisplay() {
        crystalsTextView.text = "Кристаллы: $crystals\uD83D\uDC8E"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::adTimer.isInitialized) {
            adTimer.cancel()
        }
    }
}