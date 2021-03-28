package be.appwise.simplifiedPokedex.ui.main.detail

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import be.appwise.core.ui.base.BaseVMFragment
import be.appwise.simplifiedPokedex.CustomViews.TextProgressBar
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.gone
import be.appwise.simplifiedPokedex.extensions.visible
import be.appwise.simplifiedPokedex.utils.CommonUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import kotlinx.android.synthetic.main.pokedex_entry_type_chart.*

class PokemonDetailFragment : BaseVMFragment<PokemonDetailViewModel>() {
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

    override fun getViewModel() = PokemonDetailViewModel::class.java

    private var mPokemonId: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPokemonId = requireArguments().getInt(POKEMON_ID_KEY, 0)

        mViewModel.getPokemonDetails(mPokemonId)

        initializeOnClicks()
        initializeListeners()
    }

    private fun initializeListeners() {
        mViewModel.apply {
            pokemon.observe(viewLifecycleOwner, Observer {
                fillInPokemonDetails(it)
            })

            pokemonBaseStat.observe(viewLifecycleOwner, Observer {
                fillInBaseStatDetails(it)
            })

            pokemonMatchUp.observe(viewLifecycleOwner, Observer {
                fillInMatchUpDetails(it)
            })
        }
    }

    private fun fillInPokemonDetails(pokemon: Pokemon) {
        Glide.with(this).load(
            "https://www.serebii.net/xy/pokemon/${
                CommonUtils.getCorrectNationalDexNotation(pokemon.nat_dex)
            }.png"
        ).into(pokeDrawable)
        Glide.with(this).load(
            "https://www.serebii.net/Shiny/XY/${
                CommonUtils.getCorrectNationalDexNotation(pokemon.nat_dex)
            }.png"
        ).into(pokeDrawable2)

        loadIconWithGlideWithListener(pokemon.nat_dex.minus(1), previousIv)
        loadIconWithGlideWithListener(pokemon.nat_dex.plus(1), nextIv)

        setTypeBackground(pokemon.type1, tvType1)
        setTypeBackground(pokemon.type2, tvType2)

        dex_natNoTv.text = "${pokemon.nat_dex}"
        dex_speciesTv.text = pokemon.species
        gender_dexTv.text = pokemon.gender_spread
        height_dexTv.text = pokemon.height
        weight_dexTv.text = pokemon.weight
        name_dexTv.text = pokemon.name

        descriptionXTv.text = pokemon.description_x
        descriptionYTv.text = pokemon.description_y
        locationXTv.text = pokemon.location_x
        locationYTv.text = pokemon.location_y
    }

    private fun fillInBaseStatDetails(baseStat: BaseStat) {
        fillProgressBar(hpProgressBar, "Hp: ", "${baseStat.hp}")
        fillProgressBar(attProgressBar, "Att: ", "${baseStat.att}")
        fillProgressBar(defProgressBar, "Def: ", "${baseStat.def}")
        fillProgressBar(spAttProgressBar, "Sp Att: ", "${baseStat.sp_att}")
        fillProgressBar(spDefProgressBar, "Sp Def: ", "${baseStat.sp_def}")
        fillProgressBar(speedProgressBar, "Speed: ", "${baseStat.speed}")
    }

    private fun initializeOnClicks() {
        locationTbtn.setOnCheckedChangeListener { _, isChecked ->
            hideOrShow(isChecked, locationLayout)
        }
        descriptionTbtn.setOnCheckedChangeListener { _, isChecked ->
            hideOrShow(isChecked, descriptionLayout)
        }

        nextIv.setOnClickListener {
            mPokemonId = mPokemonId.plus(1)
            mViewModel.getPokemonDetails(mPokemonId)
        }

        previousIv.setOnClickListener {
            mPokemonId = mPokemonId.minus(1)
            mViewModel.getPokemonDetails(mPokemonId)
        }
    }

    private fun hideOrShow(isChecked: Boolean, locationLayout: LinearLayout?) {
        TransitionManager.beginDelayedTransition(dexScroll)
        if (isChecked) {
            locationLayout?.visible()
        } else {
            locationLayout?.gone()
        }
    }

    private fun fillInMatchUpDetails(matchUp: MatchUp) {
        // TODO: when possible, create a GridLayout for this!!
        setTextAndColor(bugTv, matchUp.bug)
        setTextAndColor(darkTv, matchUp.dark)
        setTextAndColor(dragonTv, matchUp.dragon)
        setTextAndColor(electricTv, matchUp.electric)
        setTextAndColor(fairyTv, matchUp.fairy)
        setTextAndColor(fightTv, matchUp.fight)
        setTextAndColor(fireTv, matchUp.fire)
        setTextAndColor(flyingTv, matchUp.flying)
        setTextAndColor(ghostTv, matchUp.ghost)
        setTextAndColor(grassTv, matchUp.grass)
        setTextAndColor(groundTv, matchUp.ground)
        setTextAndColor(iceTv, matchUp.ice)
        setTextAndColor(normalTv, matchUp.normal)
        setTextAndColor(poisonTv, matchUp.poison)
        setTextAndColor(psychicTv, matchUp.psychic)
        setTextAndColor(rockTv, matchUp.rock)
        setTextAndColor(steelTv, matchUp.steel)
        setTextAndColor(waterTv, matchUp.water)
    }

    private fun loadIconWithGlideWithListener(natDex: Int?, imageView: ImageView) {
        Glide.with(this)
            .load(
                "https://www.serebii.net/pokedex-xy/icon/${
                    CommonUtils.getCorrectNationalDexNotation(
                        natDex
                    )
                }.png"
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.gone()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    imageView.visible()
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
                        requireActivity(),
                        R.color.x0_0
                    )
                )
                data.contains("x0.25") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.x0_25
                    )
                )
                data.contains("x0.5") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.x0_5
                    )
                )
                data.contains("x2.0") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.x2_0
                    )
                )
                data.contains("x4.0") -> textView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
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
            val resID =
                resources.getIdentifier(mDrawable, "drawable", requireActivity().packageName)
            typeBtn.text = type
            typeBtn.setBackgroundResource(resID)
        }
    }

    private fun fillProgressBar(
        textProgressBar: TextProgressBar,
        baseStatName: String,
        baseStatData: String
    ) {
        textProgressBar.text = baseStatName + baseStatData
        textProgressBar.progress = Integer.valueOf(baseStatData)
    }

}
