package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.Constants
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class MarvelHeroDao {


    @Query("SELECT * FROM "+ Constants.MARVEL_HERO_ENTITY)
    abstract fun getAllMarvelHeroes(): Maybe<List<MarvelHeroEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(marvelHeroes: List<MarvelHeroEntity>)

    @Query("DELETE FROM "+ Constants.MARVEL_HERO_ENTITY)
    abstract fun removeAll()


    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateMarvelHero(marvelHero: MarvelHeroEntity):Int

    @Transaction
    open fun removeAndInsertMarvelHero(marvelHeroes: List<MarvelHeroEntity>){
        //removeAll()
        insertAll(marvelHeroes)
    }

}