package com.example.technicaltest.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.databinding.ItemFavoriteBinding
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.util.loadImage

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun render(character: CharacterModel) = with(binding) {
        binding.imageCharacter.loadImage(character.image)
        binding.tvName.text = character.name
    }
}