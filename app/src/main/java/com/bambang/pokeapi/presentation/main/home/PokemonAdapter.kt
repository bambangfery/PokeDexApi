package com.bambang.pokeapi.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bambang.pokeapi.databinding.ItemPokemonBinding
import com.bambang.pokeapi.domain.model.Pokemon
import com.bumptech.glide.Glide

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val list = mutableListOf<Pokemon>()

    fun submitList(newList: List<Pokemon>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.tvPokemonName.text = pokemon.name
            Glide.with(binding.root).load(pokemon.url).into(binding.imgPokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}