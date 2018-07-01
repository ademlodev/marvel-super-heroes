package com.costular.marvelheroes.presentation.util.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.costular.marvelheroes.presentation.heroedetail.MarvelHeroDetailViewModel
import com.costular.marvelheroes.presentation.heroeslist.HeroesListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HeroesListViewModel::class)
    internal abstract fun postListViewModel(viewModel: HeroesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MarvelHeroDetailViewModel::class)
    internal abstract fun postDetailViewModel(viewModel: MarvelHeroDetailViewModel): ViewModel
    //Add more ViewModels here

}