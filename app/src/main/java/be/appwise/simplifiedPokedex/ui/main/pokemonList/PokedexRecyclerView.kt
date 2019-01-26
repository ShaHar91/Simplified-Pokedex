package be.appwise.simplifiedPokedex.ui.main.pokemonList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.typeBackground
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_pokedex.view.*
import android.graphics.Color


class PokedexRecyclerView(private val myDataset: List<Pokemon>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(private val holder: View) : RecyclerView.ViewHolder(holder) {
        fun onBind(pokemon: Pokemon, position: Int, listener: (Pokemon, Int, View) -> Unit) {
            val str = String.format("%03d", pokemon.nat_dex)

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
            holder.onBind(myDataset[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}