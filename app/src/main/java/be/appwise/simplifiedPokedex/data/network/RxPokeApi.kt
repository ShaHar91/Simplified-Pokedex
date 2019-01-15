package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.*
import io.reactivex.Observable

interface RxPokeApi {
    fun getPokemon(): Observable<List<Pokemon>>
}