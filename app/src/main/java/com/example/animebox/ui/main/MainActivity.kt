package com.example.animebox.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.animebox.R
import com.example.animebox.data.repository.CharacterRepository
import com.example.animebox.data.repository.CrystalsRepository
import com.example.animebox.domain.usecase.GetCharactersUseCase
import com.example.animebox.domain.usecase.ManageCrystalsUseCase
import com.example.animebox.ui.advertisement.AdDialog
import com.example.animebox.ui.character.CharacterDialog

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var crystalsTextView: TextView
    private lateinit var btnWatchAd: Button
    private var isAdShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupViewModel()
        setupViews()
        setupObservers()
    }

    private fun setupViewModel() {
        val characterRepository = CharacterRepository()
        val crystalsRepository = CrystalsRepository(this)
        val getCharactersUseCase = GetCharactersUseCase(characterRepository)
        val manageCrystalsUseCase = ManageCrystalsUseCase(crystalsRepository)

        viewModel = ViewModelProvider(this, MainViewModelFactory(
            getCharactersUseCase,
            manageCrystalsUseCase
        ))[MainViewModel::class.java]
    }

    private fun setupViews() {
        crystalsTextView = findViewById(R.id.crystalsTextView)
        btnWatchAd = findViewById(R.id.btnWatchAd)

        findViewById<Button>(R.id.btnOpenNarutoBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.NARUTO) 
        }
        findViewById<Button>(R.id.btnOpenKaguyaSamaBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.KAGUYA) 
        }
        findViewById<Button>(R.id.btnOpenChainsawmanBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.CHAINSAWMAN) 
        }
        findViewById<Button>(R.id.btnOpenBocchitheRockerSamaBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.BOCCHI) 
        }
        findViewById<Button>(R.id.btnOpenJojoBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.JOJO) 
        }
        findViewById<Button>(R.id.btnOpenWindBreakerBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.WINDBREAKER) 
        }
        findViewById<Button>(R.id.btnOpenDungeonMeshiBox).setOnClickListener { 
            openAnimeBox(MainViewModel.AnimeType.DUNGEONMESHI) 
        }
        btnWatchAd.setOnClickListener { showAd() }
    }

    private fun setupObservers() {
        viewModel.crystals.observe(this) { crystals ->
            crystalsTextView.text = "Кристаллы: $crystals\uD83D\uDC8E"
        }

        viewModel.selectedCharacter.observe(this) { character ->
            CharacterDialog(this).show(character)
        }
    }

    private fun openAnimeBox(animeType: MainViewModel.AnimeType) {
        if (!viewModel.tryOpenLootbox(animeType)) {
            Toast.makeText(this, "Недостаточно кристаллов!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAd() {
        if (!isAdShowing) {
            isAdShowing = true
            btnWatchAd.isEnabled = false

            AdDialog(this) {
                viewModel.onAdCompleted()
                btnWatchAd.isEnabled = true
                isAdShowing = false
                Toast.makeText(this, "+200 кристаллов\uD83D\uDC8E!", Toast.LENGTH_SHORT).show()
            }.show()
        }
    }
} 