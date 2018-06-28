package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.SettingsManager
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HeroesListViewModel @Inject constructor(
            val marvelHeroesRepository: MarvelHeroesRepository,
            val settingsManager: SettingsManager)
    : BaseViewModel() {

    val marvelListState: MutableLiveData<List<MarvelHeroEntity>> = MutableLiveData()
    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()

    fun loadMarvelHeroes(){
        marvelHeroesRepository.getMarvelHeroesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoadingState.postValue(true) }
                .doOnTerminate { isLoadingState.postValue(false) }
                .subscribeBy (
                    onNext = {
                        marvelListState.value = it
                    },
                    onError = {
                        Log.d("MarvelViewModel", it.toString())
                    },
                    onComplete = {
                        settingsManager.firstLoad = false
                    }
                )
                .addTo(compositeDisposable)
    }
}