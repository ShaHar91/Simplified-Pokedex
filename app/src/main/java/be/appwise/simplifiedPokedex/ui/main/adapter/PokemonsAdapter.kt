package be.appwise.simplifiedPokedex.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.typeBackground
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_pokedex.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import be.appwise.simplifiedPokedex.utils.CommonUtils

class PokedexRecyclerView(private val listener: (Pokemon, Int, View) -> Unit) :
    ListAdapter<Pokemon, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokedex, parent, false)

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (holder is MyViewHolder) {
            holder.onBind(getItem(position), position, listener)
        }
    }

    inner class MyViewHolder(private val holder: View) : RecyclerView.ViewHolder(holder) {
        fun onBind(pokemon: Pokemon, position: Int, listener: (Pokemon, Int, View) -> Unit) {
            val str = CommonUtils.getCorrectNationalDexNotation(pokemon.nat_dex)

            holder.setOnClickListener {
                listener(pokemon, position, holder)
            }

            Glide.with(holder).load("https://www.serebii.net/pokedex-xy/icon/$str.png").into(holder.ivPokemonIcon)
            holder.ivPokemonIcon
            holder.tvPokemonNatNumber.text = str
            holder.tvPokemonName.text = pokemon.name
            holder.tvType1.typeBackground(pokemon.type1)
            holder.tvType2.typeBackground(pokemon.type2)
        }
    }
}

class PokemonDiffCallback: DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}