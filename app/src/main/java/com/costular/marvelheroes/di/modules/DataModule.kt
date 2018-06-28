package com.costular.marvelheroes.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.costular.marvelheroes.data.db.MarvelHeroDatabase
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl
import com.costular.marvelheroes.data.repository.datasource.LocalDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.presentation.util.SettingsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by costular on 17/03/2018.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMarvelHeroMapper(): MarvelHeroMapper = MarvelHeroMapper()

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideSettingsManager(sharedPreferences: SharedPreferences): SettingsManager =
            SettingsManager(sharedPreferences)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MarvelHeroDatabase =
            Room.databaseBuilder(context,  MarvelHeroDatabase::class.java, "marvelhero.db").build()

    @Provides
    @Singleton
    fun provideLocalDataSource(marvelHeroDatabase: MarvelHeroDatabase): LocalDataSource = LocalDataSource(marvelHeroDatabase)

    @Provides
    @Singleton
    fun provideRemoteMarvelHeroesDataSoruce(marvelHeroesService: MarvelHeroesService,
                                            marvelHeroMapper: MarvelHeroMapper)
            : RemoteMarvelHeroesDataSource =
            RemoteMarvelHeroesDataSource(marvelHeroesService,marvelHeroMapper)

    @Provides
    @Singleton
    fun provideMarvelHeroesRepository(
            marvelLocalMarvelHeroesDataSource: LocalDataSource,
            marvelRemoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource,
            marvelHeroMapper: MarvelHeroMapper): MarvelHeroesRepository =
            MarvelHeroesRepositoryImpl(marvelLocalMarvelHeroesDataSource, marvelRemoteMarvelHeroesDataSource, marvelHeroMapper)

}