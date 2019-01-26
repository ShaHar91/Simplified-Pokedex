package be.appwise.simplifiedPokedex.ui.main.pokemonList

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import be.appwise.simplifiedPokedex.CustomViews.TextProgressBar
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.gone
import be.appwise.simplifiedPokedex.extensions.visible
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.pokedex_entry_type_chart.*
import kotlinx.android.synthetic.main.pokemon_detail.*

class PokemonDetailFragment : Fragment() {
    companion object {
        const val TAG = "PokemonDetailFragment"
        const val POKEMON_ID_KEY = "pokemonId"

        fun newInstance(pokemonId: Int): PokemonDetailFragment {
            val fragment = PokemonDetailFragment()
            val bundle = Bundle()
            bundle.putInt(POKEMON_ID_KEY, pokemonId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var compositeDisposable = CompositeDisposable()
    private lateinit var parentActivity: MainActivity
    private var pokemon: Pokemon? = null
    private var pokemonBaseStat: BaseStat? = null
    private var pokemonMatchUp: MatchUp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pokemon_detail, container, false)

        val pokemonId = arguments?.getInt(POKEMON_ID_KEY, 0)

        fetchPokemonById(pokemonId!!)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeOnClicks()
    }

    private fun initializeOnClicks() {
        locationTbtn.setOnCheckedChangeListener { _, isChecked ->
            hideOrShow(isChecked, locationLayout)
        }
        descriptionTbtn.setOnCheckedChangeListener { _, isChecked ->
            hideOrShow(isChecked, descriptionLayout)
        }
    }

    private fun hideOrShow(isChecked: Boolean, locationLayout: LinearLayout?) {
        if (isChecked) {
            locationLayout?.visible()
        } else {
            locationLayout?.gone()
        }
    }

    private fun fetchPokemonById(pokemonId: Int) {
        val mDb = SimplifiedPokedexDatabase.getInstance(parentActivity)
        val disposable = Observable.fromCallable {
            mDb?.pokemonDao()?.getPokemonById(pokemonId)
        }
            .subscribeOn(Schedulers.io())
            .switchMap {
                pokemon = it

                return@switchMap Observable.fromCallable { mDb?.baseStatDao()?.getBaseStatById(pokemonId) }
            }
            .subscribeOn(Schedulers.io())
            .switchMap {
                pokemonBaseStat = it

                return@switchMap Observable.fromCallable {
                    mDb?.matchUpDao()?.getMatchUpForTypes(pokemon?.type1, pokemon?.type2)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pokemonMatchUp = it
            }, {
                it.printStackTrace()
            }, {
                fillInLayout()
            })

        compositeDisposable.add(disposable)
    }

    private fun fillInLayout() {

        Glide.with(this)
            .load("https://www.serebii.net/xy/pokemon/${getCorrectNationalDexNotation(pokemon?.nat_dex)}.png")
            .into(pokeDrawable)
        Glide.with(this).load("https://www.serebii.net/Shiny/XY/${getCorrectNationalDexNotation(pokemon?.nat_dex)}.png")
            .into(pokeDrawable2)

        Glide.with(this)
            .load("https://www.serebii.net/pokedex-xy/icon/${getCorrectNationalDexNotation(pokemon?.nat_dex?.minus(1))}.png")
            .into(previousIv)
        Glide.with(this)
            .load("https://www.serebii.net/pokedex-xy/icon/${getCorrectNationalDexNotation(pokemon?.nat_dex?.plus(1))}.png")
            .into(nextIv)

        setTypeBackground(pokemon?.type1, tvType1)
        setTypeBackground(pokemon?.type2, tvType2)

        dex_natNoTv.text = "${pokemon?.nat_dex}"
        dex_speciesTv.text = pokemon?.species
        gender_dexTv.text = pokemon?.gender_spread
        height_dexTv.text = pokemon?.height
        weight_dexTv.text = pokemon?.weight
        name_dexTv.text = pokemon?.name

        descriptionXTv.text = pokemon?.description_x
        descriptionYTv.text = pokemon?.description_y
        locationXTv.text = pokemon?.location_x
        locationYTv.text = pokemon?.location_y

        fillProgressBar(hpProgressBar, "Hp: ", "${pokemonBaseStat?.hp}")
        fillProgressBar(attProgressBar, "Att: ", "${pokemonBaseStat?.att}")
        fillProgressBar(defProgressBar, "Def: ", "${pokemonBaseStat?.def}")
        fillProgressBar(spAttProgressBar, "Sp Att: ", "${pokemonBaseStat?.sp_att}")
        fillProgressBar(spDefProgressBar, "Sp Def: ", "${pokemonBaseStat?.sp_def}")
        fillProgressBar(speedProgressBar, "Speed: ", "${pokemonBaseStat?.speed}")

        setTextAndColor(bugTv, pokemonMatchUp?.bug)
        setTextAndColor(darkTv, pokemonMatchUp?.dark)
        setTextAndColor(dragonTv, pokemonMatchUp?.dragon)
        setTextAndColor(electricTv, pokemonMatchUp?.electric)
        setTextAndColor(fairyTv, pokemonMatchUp?.fairy)
        setTextAndColor(fightTv, pokemonMatchUp?.fight)
        setTextAndColor(fireTv, pokemonMatchUp?.fire)
        setTextAndColor(flyingTv, pokemonMatchUp?.flying)
        setTextAndColor(ghostTv, pokemonMatchUp?.ghost)
        setTextAndColor(grassTv, pokemonMatchUp?.grass)
        setTextAndColor(groundTv, pokemonMatchUp?.ground)
        setTextAndColor(iceTv, pokemonMatchUp?.ice)
        setTextAndColor(normalTv, pokemonMatchUp?.normal)
        setTextAndColor(poisonTv, pokemonMatchUp?.poison)
        setTextAndColor(psychicTv, pokemonMatchUp?.psychic)
        setTextAndColor(rockTv, pokemonMatchUp?.rock)
        setTextAndColor(steelTv, pokemonMatchUp?.steel)
        setTextAndColor(waterTv, pokemonMatchUp?.water)
    }


    private fun setTextAndColor(textView: TextView, data: String?) {
        textView.text = data

        if (!data.isNullOrEmpty()) {
            when {
                data.contains("x0.0") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        parentActivity,
                        R.color.x0_0
                    )
                )
                data.contains("x0.25") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        parentActivity,
                        R.color.x0_25
                    )
                )
                data.contains("x0.5") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        parentActivity,
                        R.color.x0_5
                    )
                )
                data.contains("x2.0") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        parentActivity,
                        R.color.x2_0
                    )
                )
                data.contains("x4.0") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        parentActivity,
                        R.color.x4_0
                    )
                )
                else -> textView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    private fun getCorrectNationalDexNotation(nat_dex: Int?): String {
        return String.format("%03d", nat_dex)
    }

    private fun setTypeBackground(type: String?, typeBtn: TextView) {
        if (TextUtils.isEmpty(type)) {
            typeBtn.visibility = View.GONE
        } else {
            val mDrawable = "button_border_${type?.toLowerCase()}"
            val resID = resources.getIdentifier(mDrawable, "drawable", parentActivity.packageName)
            typeBtn.text = type
            typeBtn.setBackgroundResource(resID)
        }
    }

    private fun fillProgressBar(textProgressBar: TextProgressBar, baseStatName: String, baseStatData: String) {
        textProgressBar.text = baseStatName + baseStatData
        textProgressBar.progress = Integer.valueOf(baseStatData)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        SimplifiedPokedexDatabase.destroyInstance()
        super.onDestroy()
    }
}
