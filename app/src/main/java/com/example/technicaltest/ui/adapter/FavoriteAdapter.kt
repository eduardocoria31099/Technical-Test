package com.example.technicaltest.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.databinding.ItemFavoriteBinding
import com.example.technicaltest.domain.model.CharacterModel

class FavoriteAdapter(
    private var character: List<CharacterModel> = emptyList()
) : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = character.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.render(character[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(character: List<CharacterModel>) {
        this.character = character
        notifyDataSetChanged()
    }
}