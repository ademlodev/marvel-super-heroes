package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.datasource.LocalDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepositoryImpl (private val localDataSource: LocalDataSource,
                                  private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource,
                                  private val marvelHeroesMapper: MarvelHeroMapper)
    : MarvelHeroesRepository {
    override fun updateMarvelHero(marvelHeroEntity: MarvelHeroEntity) {
        localDataSource.updateMarvelHero(marvelHeroEntity)
    }

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromApi()
                    .concatWith(getMarvelHeroesFromDb() )

    private fun getMarvelHeroesFromDb(): Flowable<List<MarvelHeroEntity>> = localDataSource.getMarvelHeroesList()
    private fun getMarvelHeroesFromApi(): Flowable<List<MarvelHeroEntity>> =
            remoteMarvelHeroesDataSource.getMarvelHeroesList().doOnNext {
                localDataSource.saveMarvelHeroes(it)
            }
}