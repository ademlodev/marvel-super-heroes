package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MarvelHeroDetailViewModel @Inject constructor(
        val marvelHeroesRepository: MarvelHeroesRepository)
    : BaseViewModel() {

    val marvelDetailState: MutableLiveData<MarvelHeroEntity> = MutableLiveData()

    fun updateMarvelHero(marvelHeroEntity: MarvelHeroEntity){
        Observable.fromCallable {
            marvelHeroesRepository.updateMarvelHero(marvelHeroEntity)
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{
            marvelDetailState.value = marvelHeroEntity
        }
        .addTo(compositeDisposable)

    }
}