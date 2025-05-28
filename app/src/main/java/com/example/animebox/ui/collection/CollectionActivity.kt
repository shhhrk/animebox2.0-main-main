package com.example.animebox.ui.collection

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.animebox.R
import com.example.animebox.data.repository.CharacterRepository

class CollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val gridView: GridView = findViewById(R.id.collectionGridView)
        val repository = CharacterRepository()
        repository.init(this)
        val collectedNames = repository.getUserCollection().toList()
        val allCharacters = repository.getAllCharacters()

        val adapter = CollectionAdapter(this, collectedNames, allCharacters)
        gridView.adapter = adapter

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnClearCollection).setOnClickListener {
            repository.clearUserCollection()
            val newCollectedNames = repository.getUserCollection().toList()
            val newAdapter = CollectionAdapter(this, newCollectedNames, allCharacters)
            gridView.adapter = newAdapter
        }
    }
} 