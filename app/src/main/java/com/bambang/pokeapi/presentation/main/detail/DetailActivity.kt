package com.bambang.pokeapi.presentation.main.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bambang.pokeapi.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Pokémon"

        val name = intent.getStringExtra("name") ?: return

        viewModel.loadPokemonDetail(name)

        lifecycleScope.launch {
            viewModel.pokemonDetail.collectLatest { detail ->
                detail?.let {
                    binding.tvPokemonName.text = it.name.capitalize()
                    Glide.with(this@DetailActivity)
                        .load(it.sprites.frontDefault)
                        .into(binding.imgPokemon)

                    val abilityNames = it.abilities.joinToString(", ") { a -> a.ability.name }
                    binding.tvAbilities.text = "Abilities : $abilityNames"
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { loading ->
                binding.progressBar.isVisible = loading
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collectLatest { msg ->
                msg?.let {
                    Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}