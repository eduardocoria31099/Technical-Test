package com.example.technicaltest.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityFavoritesBinding
import com.example.technicaltest.ui.adapter.FavoriteAdapter
import com.example.technicaltest.ui.viewmodel.FavoritesViewModel
import com.example.technicaltest.util.collect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    private lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initView()
        setListeners()
        setAdapter()
        setRecycler()
        setFlow()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener { finish() }
    }

    private fun setAdapter() {
        favoriteAdapter = FavoriteAdapter()
    }

    private fun setRecycler() {
        binding.rvFavorites.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@FavoritesActivity, 2)
            adapter = favoriteAdapter
        }
    }

    private fun setFlow() {
        collect(viewModel.favoriteCharacters) { charactersList ->
            favoriteAdapter.setList(charactersList)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCharacters()
    }
}