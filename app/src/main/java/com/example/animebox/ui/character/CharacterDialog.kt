package com.example.animebox.ui.character

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.animebox.R
import com.example.animebox.domain.model.Character

class CharacterDialog(private val context: Context) {
    
    fun show(character: Character) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.character_dialog, null)
        
        dialogView.findViewById<TextView>(R.id.characterName).text = character.name
        dialogView.findViewById<TextView>(R.id.characterRarity).text = "Редкость: ${character.rarity}"
        dialogView.findViewById<ImageView>(R.id.characterImage).setImageResource(character.imageRes)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
} 