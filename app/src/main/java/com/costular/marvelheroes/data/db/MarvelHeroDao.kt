package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class MarvelHeroDao {

    @Query("SELECT * FROM marvelhero")
    abstract fun getAllMarvelHeroes(): Maybe<List<MarvelHeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(marvelHeroes: List<MarvelHeroEntity>)

    @Query("DELETE FROM marvelhero")
    abstract fun removeAll()

    @Transaction
    open fun removeAndInsertMarvelHero(marvelHeroes: List<MarvelHeroEntity>){
        removeAll()
        insertAll(marvelHeroes)
    }


}