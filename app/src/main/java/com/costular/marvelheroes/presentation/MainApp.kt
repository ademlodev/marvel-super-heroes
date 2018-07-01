package com.costular.marvelheroes.presentation

import android.app.Application
import com.costular.marvelheroes.di.components.ApplicationComponent
import com.costular.marvelheroes.di.components.DaggerApplicationComponent
import com.costular.marvelheroes.di.modules.ApplicationModule
import com.facebook.stetho.Stetho

/**
 * Created by costular on 16/03/2018.
 */
class MainApp : Application() {

   /* val component: ApplicationComponent by lazy {

        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }*/

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
//        this.deleteDatabase("marvelhero.db")
        // DI
        component =
                DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
    }


}