package com.costular.marvelheroes.data.db.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GroupsArrayConverter {
    @TypeConverter
    fun fromString(value: String): Array<String> = Gson().fromJson(value, object : TypeToken<Array<String>>() {}.type)

    @TypeConverter
    fun fromModel(value: Array<String>): String = Gson().toJson(value)

}