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
import android.widget.Filter
import android.widget.Filterable
import be.appwise.simplifiedPokedex.ui.utils.CommonUtils

class PokedexRecyclerView(private var myDataset: List<Pokemon>, private val listener: (Pokemon, Int, View) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var filteredData: List<Pokemon>

    init {
        filteredData = myDataset
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredData = if (charString.isEmpty()) {
                    myDataset
                } else {
                    myDataset.filter { it.name.contains(charString, true) }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredData
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as List<Pokemon>

                notifyDataSetChanged()
            }
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(private val holder: View) : RecyclerView.ViewHolder(holder) {
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
            holder.onBind(filteredData[position], position, listener)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = filteredData.size
}