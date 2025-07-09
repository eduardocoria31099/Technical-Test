package com.example.technicaltest.ui.view

import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.FragmentActivity
import com.example.technicaltest.databinding.DialogCharacterDetailBinding
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.util.loadImage

class CharacterDetailDialog(
    context: FragmentActivity,
    private val onClickListener: (Boolean, CharacterModel) -> Unit,
    private val onClickListenerMap: () -> Unit,
) {

    private var binding: DialogCharacterDetailBinding =
        DialogCharacterDetailBinding.inflate(LayoutInflater.from(context))

    private var dialog: AlertDialog

    private lateinit var character: CharacterModel


    init {
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        setListener()
    }

    private fun setListener() = with(binding) {
        btExit.setOnClickListener { hide() }
        cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            onClickListener.invoke(isChecked, character)
        }
        btLocation.setOnClickListener {
            onClickListenerMap.invoke()
            hide()
        }
    }

    fun show() = dialog.show()

    private fun hide() = dialog.dismiss()

    fun setCancelable(isCancelable: Boolean) =
        dialog.setCancelable(isCancelable)

    fun setData(character: CharacterModel) = with(binding) {
        this@CharacterDetailDialog.character = character
        character.apply {
            ivCharacter.loadImage(image)
            tvGender.text = gender
            tvSpecie.text = species
            tvState.text = status
            tvLocation.text = locationName
            cbFavorite.isChecked = isFavorite
        }
    }

}