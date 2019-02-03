package be.appwise.simplifiedPokedex.ui.main.pokemonList

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import be.appwise.simplifiedPokedex.ui.utils.CommonUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.pokedex_entry_type_chart.*
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*

class PokemonDetailFragment : Fragment() {
    companion object {
        const val TAG = "PokemonDetailFragment"
        const val POKEMON_ID_KEY = "mPokemonId"

        fun newInstance(pokemonId: Int): PokemonDetailFragment {
            val fragment = PokemonDetailFragment()
            val bundle = Bundle()
            bundle.putInt(POKEMON_ID_KEY, pokemonId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var compositeDisposable = CompositeDisposable()
    private lateinit var parentActivity: AppCompatActivity
    private var mPokemon: Pokemon? = null
    private var mPokemonBaseStat: BaseStat? = null
    private var mPokemonMatchUp: MatchUp? = null
    private var mPokemonId: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = activity as AppCompatActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPokemonId = arguments?.getInt(POKEMON_ID_KEY, 0)

        fetchPokemonById(mPokemonId ?: 1)

        initializeOnClicks()
    }

    private fun initializeOnClicks() {
        locationTbtn.setOnCheckedChangeListener { _, isChecked ->
            hideOrShow(isChecked, locationLayout)
        }
        descriptionTbtn.setOnCheckedChangeListener { _, isChecked ->
            hideOrShow(isChecked, descriptionLayout)
        }

        nextIv.setOnClickListener {
            mPokemonId = mPokemonId?.plus(1)
            fetchPokemonById(mPokemonId ?: 1)
        }

        previousIv.setOnClickListener {
            mPokemonId = mPokemonId?.minus(1)
            fetchPokemonById(mPokemonId ?: 1)
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
                mPokemon = it

                return@switchMap Observable.fromCallable { mDb?.baseStatDao()?.getBaseStatById(pokemonId) }
            }
            .subscribeOn(Schedulers.io())
            .switchMap {
                mPokemonBaseStat = it

                return@switchMap Observable.fromCallable {
                    mDb?.matchUpDao()?.getMatchUpForTypes(mPokemon?.type1, mPokemon?.type2)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mPokemonMatchUp = it
            }, {
                it.printStackTrace()
            }, {
                fillInLayout()
            })

        compositeDisposable.add(disposable)
    }

    private fun fillInLayout() {
        Glide.with(this)
            .load("https://www.serebii.net/xy/pokemon/${CommonUtils.getCorrectNationalDexNotation(mPokemon?.nat_dex)}.png")
            .into(pokeDrawable)
        Glide.with(this)
            .load("https://www.serebii.net/Shiny/XY/${CommonUtils.getCorrectNationalDexNotation(mPokemon?.nat_dex)}.png")
            .into(pokeDrawable2)

        loadIconWithGlideWithListener(mPokemon?.nat_dex?.minus(1), previousIv)
        loadIconWithGlideWithListener(mPokemon?.nat_dex?.plus(1), nextIv)

        setTypeBackground(mPokemon?.type1, tvType1)
        setTypeBackground(mPokemon?.type2, tvType2)

        dex_natNoTv.text = "${mPokemon?.nat_dex}"
        dex_speciesTv.text = mPokemon?.species
        gender_dexTv.text = mPokemon?.gender_spread
        height_dexTv.text = mPokemon?.height
        weight_dexTv.text = mPokemon?.weight
        name_dexTv.text = mPokemon?.name

        descriptionXTv.text = mPokemon?.description_x
        descriptionYTv.text = mPokemon?.description_y
        locationXTv.text = mPokemon?.location_x
        locationYTv.text = mPokemon?.location_y

        fillProgressBar(hpProgressBar, "Hp: ", "${mPokemonBaseStat?.hp}")
        fillProgressBar(attProgressBar, "Att: ", "${mPokemonBaseStat?.att}")
        fillProgressBar(defProgressBar, "Def: ", "${mPokemonBaseStat?.def}")
        fillProgressBar(spAttProgressBar, "Sp Att: ", "${mPokemonBaseStat?.sp_att}")
        fillProgressBar(spDefProgressBar, "Sp Def: ", "${mPokemonBaseStat?.sp_def}")
        fillProgressBar(speedProgressBar, "Speed: ", "${mPokemonBaseStat?.speed}")

        setTextAndColor(bugTv, mPokemonMatchUp?.bug)
        setTextAndColor(darkTv, mPokemonMatchUp?.dark)
        setTextAndColor(dragonTv, mPokemonMatchUp?.dragon)
        setTextAndColor(electricTv, mPokemonMatchUp?.electric)
        setTextAndColor(fairyTv, mPokemonMatchUp?.fairy)
        setTextAndColor(fightTv, mPokemonMatchUp?.fight)
        setTextAndColor(fireTv, mPokemonMatchUp?.fire)
        setTextAndColor(flyingTv, mPokemonMatchUp?.flying)
        setTextAndColor(ghostTv, mPokemonMatchUp?.ghost)
        setTextAndColor(grassTv, mPokemonMatchUp?.grass)
        setTextAndColor(groundTv, mPokemonMatchUp?.ground)
        setTextAndColor(iceTv, mPokemonMatchUp?.ice)
        setTextAndColor(normalTv, mPokemonMatchUp?.normal)
        setTextAndColor(poisonTv, mPokemonMatchUp?.poison)
        setTextAndColor(psychicTv, mPokemonMatchUp?.psychic)
        setTextAndColor(rockTv, mPokemonMatchUp?.rock)
        setTextAndColor(steelTv, mPokemonMatchUp?.steel)
        setTextAndColor(waterTv, mPokemonMatchUp?.water)
    }

    private fun loadIconWithGlideWithListener(natDex: Int?, imageView: ImageView) {
        Glide.with(this)
            .load("https://www.serebii.net/pokedex-xy/icon/${CommonUtils.getCorrectNationalDexNotation(natDex)}.png")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    imageView.gone()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(imageView)
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
