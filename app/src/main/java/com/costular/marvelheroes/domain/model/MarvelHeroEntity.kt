package com.costular.marvelheroes.domain.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.provider.SyncStateContract
import com.costular.marvelheroes.presentation.util.Constants
import kotlinx.android.parcel.Parcelize

/**
 * Created by costular on 17/03/2018.
 */
@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.MARVEL_HERO_ENTITY)
@Parcelize
data class MarvelHeroEntity(
        @PrimaryKey
        val name: String,
        val photoUrl: String,
        val realName: String,
        val height: String,
        val power: String,
        val abilities: String,
        val groups: Array<String>,
        var favourite: Boolean
) : Parcelable