package com.example.technicaltest.ui.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityCharacterBinding
import com.example.technicaltest.ui.adapter.CharactersAdapter
import com.example.technicaltest.ui.viewmodel.CharacterViewModel
import com.example.technicaltest.util.BiometricModel
import com.example.technicaltest.util.collect
import com.example.technicaltest.util.collectPagingData
import com.example.technicaltest.util.isBiometricAvailable
import com.example.technicaltest.util.startActivity
import com.example.technicaltest.util.useBiometric
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterBinding
    private val characterViewModel by viewModels<CharacterViewModel>()
    private val charactersAdapter = CharactersAdapter { character ->
        characterViewModel.isCharacterFavorite(character)
    }

    private var currentName: String? = null
    private var currentStatus: String? = null
    private var currentSpecies: String? = null
    val statusOptions = listOf("Todos", "alive", "dead", "unknown")
    val speciesOptions =
        listOf("Todos", "human", "alien", "robot", "mythological", "animal", "disease")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initView()
        setFlows()
        setAdapter()
        setSpinners()
        setListener()
        setSwipe()
    }

    private fun setSwipe() {
        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setListener() {
        binding.etSearchCharacter.doAfterTextChanged { text ->
            currentName = text?.toString().orEmpty().trim()
            characterViewModel.applyFilters(currentName, currentStatus, currentSpecies)
        }

        binding.spinnerStatus.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentStatus = if (position == 0) null else statusOptions[position]
                characterViewModel.applyFilters(currentName, currentStatus, currentSpecies)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerSpecies.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentSpecies = if (position == 0) null else speciesOptions[position]
                characterViewModel.applyFilters(currentName, currentStatus, currentSpecies)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            charactersAdapter.refresh()
        }

        binding.btGoToFavorites.setOnClickListener {
            initBiometric()
        }
    }

    private fun setSpinners() {

        binding.spinnerStatus.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            statusOptions
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        binding.spinnerSpecies.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, speciesOptions).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

    }

    private fun setFlows() {
        collectPagingData(characterViewModel.characters, charactersAdapter)
        collect(characterViewModel.isFavorite) {
            CharacterDetailDialog(
                context = this@CharacterActivity,
                onClickListener = { favorite, character ->
                    characterViewModel.onFavoriteToggled(character, favorite)
                },
                onClickListenerMap = {
                    startActivity<MapsActivity>()
                },
            ).apply {
                setCancelable(false)
                setData(it.second.copy(isFavorite = it.first))
                show()
            }
        }
    }

    private fun setAdapter() {
        binding.rvCharacter.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(this@CharacterActivity)
        }
    }

    private fun initBiometric() {
        if (isBiometricAvailable()) {
            useBiometric(
                biometricModel = BiometricModel(
                    authenticationError = { },
                    authenticationSucceeded = {
                        startActivity<FavoritesActivity>()
                    },
                    authenticationFailed = { },
                    textTitle = "Autenticación Biométrica",
                    textSubTitle = "Confirma tu identidad con huella digital o reconocimiento facial.",
                    textNegativeButton = "Cancelar"
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }
}