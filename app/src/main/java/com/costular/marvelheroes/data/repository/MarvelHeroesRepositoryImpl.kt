package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.datasource.LocalDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepositoryImpl (private val localDataSource: LocalDataSource,
                                  private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource,
                                  private val marvelHeroesMapper: MarvelHeroMapper)
    : MarvelHeroesRepository {

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromDb()
                    .concatWith(getMarvelHeroesFromApi())

    private fun getMarvelHeroesFromDb(): Flowable<List<MarvelHeroEntity>> = localDataSource.getMarvelHeroesList()
    private fun getMarvelHeroesFromApi(): Flowable<List<MarvelHeroEntity>> =
            remoteMarvelHeroesDataSource.getMarvelHeroesList().doOnNext {
                localDataSource.saveMarvelHeroes(it)
            }
}