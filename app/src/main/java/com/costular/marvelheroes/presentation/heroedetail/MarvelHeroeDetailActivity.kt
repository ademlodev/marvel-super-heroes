package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.costular.marvelheroes.R
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import kotlinx.android.synthetic.main.activity_hero_detail.*
import javax.inject.Inject

/**
 * Created by costular on 18/03/2018.
 */
class MarvelHeroeDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_HEROE = "heroe"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var heroesViewModel: MarvelHeroDetailViewModel

    var heroEntity: MarvelHeroEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        supportPostponeEnterTransition() // Wait for image load and then draw the animation

        setUpViewModel()
        heroEntity = intent?.extras?.getParcelable(PARAM_HEROE)
        heroEntity?.let {
            fillHeroData(it)

        }

        heroDetailFavorite.setOnClickListener{ updateFavourite() }
    }

    fun inject() {
        (application as MainApp).component.inject(this)
    }

    private fun setUpViewModel(){
        heroesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MarvelHeroDetailViewModel::class.java)
        BindEvents()
    }

    private fun BindEvents(){
        heroesViewModel.marvelDetailState.observe(this, Observer { heroDetail ->
            heroDetail?.let {
                fillHeroData(heroDetail)
            }
        })
    }

    private fun fillHeroData(hero: MarvelHeroEntity) {
        Glide.with(this)
                .load(hero.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(heroDetailImage)

        heroDetailName.text = hero.name
        heroDetailRealName.text = hero.realName
        heroDetailHeight.text = hero.height
        heroDetailPower.text = hero.power
        heroDetailAbilities.text = hero.abilities
        setFavoriteIcon(hero.favourite)
    }

    private fun updateFavourite(){

        var marvelHeroUpdated: MarvelHeroEntity
        heroEntity?.let {

            marvelHeroUpdated = heroEntity!!.copy(favourite = !heroEntity!!.favourite)
            heroesViewModel.updateMarvelHero(marvelHeroUpdated)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavoriteIcon(isFavourite: Boolean){
        heroDetailFavorite.setImageResource(
                when(isFavourite ){
                    true -> R.drawable.heart_red
                    false -> R.drawable.heart_white}
        )
    }

}