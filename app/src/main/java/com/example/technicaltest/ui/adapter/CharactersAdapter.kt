package com.example.technicaltest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest.databinding.ItemCharacterBinding
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.util.loadImage

class CharactersAdapter(
    private val onItemClick: (CharacterModel) -> Unit
) :
    PagingDataAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModel) {
            binding.tvName.text = character.name
            binding.tvStatus.text = character.status
            binding.tvSpecies.text = character.species
            binding.imageView.loadImage(character.image)
            binding.root.setOnClickListener {
                onItemClick(character)
            }
        }
    }


    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }
    }
}