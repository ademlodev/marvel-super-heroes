package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroDatabase
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalDataSource(val marvelHeroDatabase: MarvelHeroDatabase): MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            marvelHeroDatabase.getMarvelHeroDao().getAllMarvelHeroes().toFlowable()

    fun saveMarvelHeroes(marvelHeroes: List<MarvelHeroEntity>){
        Observable.fromCallable {
            marvelHeroDatabase.getMarvelHeroDao().removeAndInsertMarvelHero(marvelHeroes)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }
}