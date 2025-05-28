package com.example.animebox.ui.collection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.animebox.R
import com.example.animebox.domain.model.Character

class CollectionAdapter(
    context: Context,
    private val collectedCharacterNames: List<String>,
    private val allCharacters: List<Character>
) : ArrayAdapter<String>(context, R.layout.list_item_collection_character, collectedCharacterNames) {

    private val characterMap: Map<String, Character> = allCharacters.associateBy { it.name }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_collection_character, parent, false)
            viewHolder = ViewHolder(
                view.findViewById(R.id.characterImageView),
                view.findViewById(R.id.characterNameTextView),
                view.findViewById(R.id.characterRarityTextView)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val characterName = collectedCharacterNames[position]
        val character = characterMap[characterName]

        viewHolder.nameTextView.text = characterName
        character?.let {
            viewHolder.imageView.setImageResource(it.imageRes)
            viewHolder.rarityTextView.text = it.rarity
        } ?: run {
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher) // Placeholder if image not found
            viewHolder.rarityTextView.text = ""
        }

        return view
    }

    private data class ViewHolder(val imageView: ImageView, val nameTextView: TextView, val rarityTextView: TextView)
} 