package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.data.model.*
import io.reactivex.Observable

interface RxPokeApiRepository {
    fun getPokemon(): Observable<List<Pokemon>>

    fun getBaseStat(): Observable<List<BaseStat>>

    fun getMatchUps(): Observable<List<MatchUp>>
}